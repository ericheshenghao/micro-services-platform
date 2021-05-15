package cn.central.auth.store;

import cn.central.auth.converter.CustomUserAuthenticationConverter;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Base64;

/**
 * 资源服务器 TokenStore 配置类，使用 JWT RSA 非对称加密
 *
 * @author he
 * @date 2018/8/20 9:25
 */
@Slf4j
@ConditionalOnProperty(prefix = "siques.oauth2.token.store", name = "type", havingValue = "jwt")
public class ResJwtTokenStore {
    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }



    /**
     * jwt 令牌转换
     *
     * @return jwt
     */



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final  JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
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
