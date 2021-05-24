package cn.central.auth.config.client;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * 资源服务器 token 相关配置，jwt 相关，资源服务器也通过redis进行鉴权
 * @author he
 */
@Slf4j
@Import(BCryptPasswordEncoder.class)
public class DefaultResourceTokenConfig {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        return redisTokenStore;
    }


    @Autowired
    private  ResourceServerProperties resourceServerProperties;



    /**
     * jwt 令牌转换
     *
     * @return jwt
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }


    /**
     * 非对称密钥加密，获取 public key。
     * 自动选择加载方式。
     *
     * @return public key
     */
    private String getPubKey() {
        // 如果本地没有密钥，就从授权服务器中获取
        return StringUtils.isEmpty(resourceServerProperties.getJwt().getKeyValue())
            ? getKeyFromAuthorizationServer()
            : resourceServerProperties.getJwt().getKeyValue();
    }

    /**
     * 本地没有公钥的时候，从服务器上获取
     * 需要进行 Basic 认证
     *
     * @return public key
     */
    private String getKeyFromAuthorizationServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, encodeClient());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        String pubKey = new RestTemplate()
            .getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class, requestEntity);
        try {
            JSONObject body = objectMapper.readValue(pubKey, JSONObject.class);
            log.info("Get Key From Authorization Server.");
            return body.getStr("value");
        } catch (IOException e) {
            log.error("Get public key error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 客户端信息
     *
     * @return basic
     */
    public String encodeClient() {
        return "Basic " + Base64.getEncoder().encodeToString((resourceServerProperties.getClientId()
            + ":" + resourceServerProperties.getClientSecret()).getBytes());
    }


}
