package cn.central.oauth.config;

import cn.central.common.constant.CommonConstant;
import cn.central.common.utils.AESUtil;
import cn.central.common.utils.PasswordEncoder;
import cn.central.oauth.granter.PwdImgCodeGranter;
import cn.central.oauth.service.SysClientDetailsService;
import cn.central.oauth.service.SysUserService;
import cn.central.oauth.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.annotation.Resource;
import java.util.*;

/**
 * 鉴权服务器Token相关配置
 * @author : heshenghao
 * @date : 19:54 2020/11/13
 */
@Configuration
public class AuthorizationTokenConfig {

    /**
     * 验证码服务
     */
    @Autowired
    private ValidateCodeService validateCodeService;


    /**
     * 客户端查询服务
     */
    @Autowired
    private SysClientDetailsService clientDetailsService;

    /**
     * redis连接工厂
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 用户查询服务
     */
    @Autowired
    private SysUserService  userDetailService;

    @Autowired
    private List<TokenEnhancer> enhancers;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 生成token的服务，默认实现为DefaultTokenServices
     */
    private AuthorizationServerTokenServices tokenServices;

    /**
     * 符合认证模式
     */
    private TokenGranter tokenGranter;



    private boolean reuseRefreshToken = true;

    @Autowired
    @Qualifier("redisAuthorizationCodeServices")
    private AuthorizationCodeServices authorizationCodeServices;

    // TODO 如何自动查找实现类并注入,通过配置文件启动不同的储存方式，默认为redis储存
    @Resource
    TokenStore tokenStore;


    /**
     *  约定一个请求头，携带加密的后端服务的客户端Id与客户端密钥,前后端分离的前端与后端依然还是要看成一个整体
     *     如果未查询到该请求头，可以旁定未非法访问，直接拒绝
     */



    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain chain = new TokenEnhancerChain();

        enhancers.add((accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(2);
            //设置tenantID
            additionalInfo.putAll(accessToken.getAdditionalInformation());
            try {
                // 设置tenant提供给前端，前端发起请求时携带该id访问服务，
                // 服务通过 tenantFilter过滤并解密该ID来对资源进行隔离
                additionalInfo.put("x_tenant_id", AESUtil.aesCbcPkcs5PaddingEncrypt(authentication.getOAuth2Request().getClientId(),CommonConstant.AESKEY,CommonConstant.AESIV));
            } catch (Exception e) {
                e.printStackTrace();
            }

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        });

        chain.setTokenEnhancers(enhancers);
        return chain;
    }

    /**
     * 校验授权模式
     * @return
     */
    @Bean
    public TokenGranter tokenGranter(){
        if(tokenGranter == null){
            tokenGranter= new TokenGranter() {
                private CompositeTokenGranter delegate;
                @Override
                public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                    if(delegate==null){
                        delegate = new CompositeTokenGranter(getAllTokenGranters());
                    }
                    return delegate.grant(grantType, tokenRequest);
                }
            };
        }
        return tokenGranter;
    }
    /**
     * 所有授权模式：默认的5种模式 + 自定义的模式
     */
    private List<TokenGranter> getAllTokenGranters() {
        AuthorizationServerTokenServices tokenServices = tokenServices();

        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        //获取默认的授权模式
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
        if (authenticationManager != null) {
            // 添加密码加图形验证码模式
            tokenGranters.add(new PwdImgCodeGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory, validateCodeService));
        }
        return tokenGranters;
    }


    private AuthorizationServerTokenServices tokenServices() {
        if (tokenServices != null) {
            return tokenServices;
        }
        this.tokenServices = createDefaultTokenServices();
        return tokenServices;
    }

    /**
     * 创建默认的token生成服务
     * @return
     */
    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(reuseRefreshToken);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        addUserDetailsService(tokenServices, this.userDetailService);
        return tokenServices;
    }


    private void addUserDetailsService(DefaultTokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
    }

    /**
     * 默认的授权模式
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices
            , AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 添加授权码模式
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
        // 添加刷新令牌的模式
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加隐式授权模式
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加客户端模式
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            // 添加密码模式
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }



}
