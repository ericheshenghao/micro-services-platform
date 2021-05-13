package cn.central.oauth.granter;

import cn.central.oauth.service.ValidateCodeService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  账号密码验证码模式 继承自 ResourceOwnerPasswordTokenGranter
 * @author : heshenghao
 * @date : 20:27 2020/12/4
 */
public class PwdImgCodeGranter extends ResourceOwnerPasswordTokenGranter {
    // 与客户端传来的一致
    private static final String GRANT_TYPE = "password_code";

    private final ValidateCodeService validateCodeService;

    public PwdImgCodeGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ValidateCodeService validateCodeService) {

        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.validateCodeService = validateCodeService;
    }



    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        // 在原来账号密码的校验基础上添加校验验证码的步骤
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String deviceId = parameters.get("deviceId");
        String validCode = parameters.get("validCode");
        String username = parameters.get("username");

        validateCodeService.validate(username,deviceId, validCode);

        // 父类方法校验用户名密码等参数
        return  super.getOAuth2Authentication(client, tokenRequest);
    }


}
