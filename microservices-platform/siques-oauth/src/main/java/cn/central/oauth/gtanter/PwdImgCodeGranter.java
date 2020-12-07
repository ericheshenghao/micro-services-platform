package cn.central.oauth.gtanter;

import cn.central.log.annotation.AuditLog;
import cn.central.oauth.service.ValidateCodeService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  账号密码验证码模式
 * @author : heshenghao
 * @date : 20:27 2020/12/4
 */
public class PwdImgCodeGranter extends ResourceOwnerPasswordTokenGranter {
    private static final String GRANT_TYPE = "password_code";

    private final ValidateCodeService validateCodeService;

    public PwdImgCodeGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ValidateCodeService validateCodeService) {

        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.validateCodeService = validateCodeService;
    }



    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String deviceId = parameters.get("deviceId");
        String validCode = parameters.get("validCode");
        String username = parameters.get("username");
        //校验图形验证码
        validateCodeService.validate(username,deviceId, validCode);

        return  super.getOAuth2Authentication(client, tokenRequest);
    }


}
