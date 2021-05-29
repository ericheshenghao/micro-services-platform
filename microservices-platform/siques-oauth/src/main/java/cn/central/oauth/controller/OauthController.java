package cn.central.oauth.controller;

import cn.central.auth.util.AuthUtils;
import cn.central.common.annotation.LoginUser;
import cn.central.common.model.Result;
import cn.central.common.model.SysUser;
import cn.central.common.utils.SecurityUtils;
import cn.central.oauth.controller.dto.LoginDto;
import cn.central.oauth.service.SysClientDetailsService;
import cn.central.oauth.service.SysUserService;
import cn.central.oauth.service.ValidateCodeService;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.security.oauth2.provider.endpoint.RedirectResolver;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

/**
 * @author : heshenghao
 * @date : 17:43 2020/11/13
 */
@RestController
@RequestMapping("/validate")
@Api(tags = {"验证码接口"})
@Slf4j
public class OauthController    {

    private RedirectResolver redirectResolver = new DefaultRedirectResolver();

    private OAuth2RequestValidator oauth2RequestValidator = new DefaultOAuth2RequestValidator();

    @Autowired
    SysClientDetailsService sysClientDetailsService;

    @Resource
    AuthorizationCodeServices authorizationCodeServices;

    @Resource
    AuthorizationEndpoint authorizationEndpoint;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Resource
    TokenStore tokenStore;

    @Autowired
    OAuth2RequestFactory requestFactory;

    /**
     * 前后端分离情况下的退出登录操作
     * @param request
     */
    @GetMapping("removeToken")
    public void logOut(HttpServletRequest request){

       Assert.notNull(tokenStore, "tokenStore must be set");
        String token = request.getParameter("token");
        if (StrUtil.isEmpty(token)) {
            token = AuthUtils.extractToken(request);
        }
        if(StrUtil.isNotEmpty(token)){
            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
            OAuth2RefreshToken refreshToken;
            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
                    refreshToken = existingAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
                log.info("remove existingAccessToken!", existingAccessToken);
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }
    }


    /**
     * 验证码接口
     * @param response
     * @param request
     * @throws IOException
     */
    @GetMapping("captcha/{deviceId}")
    public void captcha(HttpServletResponse response, HttpServletRequest request, @PathVariable String deviceId) throws IOException {
        Assert.notNull(deviceId, "机器码不能为空");
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(100, 35, 4);
        // 设置类型：字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        // 保存验证码
        validateCodeService.saveImageCode(deviceId, gifCaptcha.text().toLowerCase());
        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }


    /**
     * 重写方法进行自定义返回code
     * @param parameters
     * @param sessionStatus
     * @return
     */
    @GetMapping(value = "/authorize")
    public Result authorize(@RequestParam Map<String, String> parameters,
                            SessionStatus sessionStatus) {
        Authentication authentication = SecurityUtils.getAuthentication();

        // Pull out the authorization request first, using the OAuth2RequestFactory. All further logic should
        // query off of the authorization request instead of referring back to the parameters map. The contents of the
        // parameters map will be stored without change in the AuthorizationRequest object once it is created.

        AuthorizationRequest authorizationRequest = requestFactory.createAuthorizationRequest(parameters);

        Set<String> responseTypes = authorizationRequest.getResponseTypes();

        if (!responseTypes.contains("token") && !responseTypes.contains("code")) {
            throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);
        }

        if (authorizationRequest.getClientId() == null) {
            throw new InvalidClientException("A client id must be provided");
        }

        try {

            if (!(authentication instanceof Authentication) || !authentication.isAuthenticated()) {
                throw new InsufficientAuthenticationException(
                        "User must be authenticated with Spring Security before authorization can be completed.");
            }

            ClientDetails client = sysClientDetailsService.loadClientByClientId(authorizationRequest.getClientId());

            // The resolved redirect URI is either the redirect_uri from the parameters or the one from
            // clientDetails. Either way we need to store it on the AuthorizationRequest.

            String redirectUriParameter = authorizationRequest.getRequestParameters().get(OAuth2Utils.REDIRECT_URI);
            // 此处会进行一个判断，判断该回调链接与我们的客户端id是否匹配，因此修改前端参数没有问题
            String resolvedRedirect = redirectResolver.resolveRedirect(redirectUriParameter, client);
            if (!StringUtils.hasText(resolvedRedirect)) {
                throw new RedirectMismatchException(
                        "A redirectUri must be either supplied or preconfigured in the ClientDetails");
            }


            // We intentionally only validate the parameters requested by the client (ignoring any data that may have
            // been added to the request by the manager).
            oauth2RequestValidator.validateScope(authorizationRequest, client);

            authorizationRequest.setApproved(true);

            // Validation is all done, so we can check for auto approval...

                return    Result.succeed(generateCode(authorizationRequest,
                         authentication),"code返回成功") ;
//
//
//            // Store authorizationRequest AND an immutable Map of authorizationRequest in session
//            // which will be used to validate against in approveOrDeny()
//            model.put(AUTHORIZATION_REQUEST_ATTR_NAME, authorizationRequest);
//            model.put(ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME, unmodifiableMap(authorizationRequest));
//
//            return getUserApprovalPageResponse(model, authorizationRequest, (Authentication) principal);

        }
        catch (RuntimeException e) {
            sessionStatus.setComplete();
            throw e;
        }

    }

//    private OAuth2RequestFactory getOAuth2RequestFactory()  {
//
//        try {
//            Field[] declaredFields = AbstractEndpoint.class.getDeclaredFields();
//
//            Field field = declaredFields[4];
//            field.setAccessible(true);
//            System.out.println(field);
//            return ( OAuth2RequestFactory) field.get(authorizationEndpoint);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    private String generateCode(AuthorizationRequest authorizationRequest, Authentication authentication)
            throws AuthenticationException {

        try {

            OAuth2Request storedOAuth2Request = requestFactory.createOAuth2Request(authorizationRequest);

            OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);
            String code = authorizationCodeServices.createAuthorizationCode(combinedAuth);

            return code;

        }
        catch (OAuth2Exception e) {

            if (authorizationRequest.getState() != null) {
                e.addAdditionalInformation("state", authorizationRequest.getState());
            }

            throw e;

        }
    }


}
