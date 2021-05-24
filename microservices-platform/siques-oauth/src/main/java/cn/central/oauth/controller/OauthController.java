package cn.central.oauth.controller;

import cn.central.auth.util.AuthUtils;
import cn.central.common.annotation.LoginUser;
import cn.central.common.model.Result;
import cn.central.common.utils.SecurityUtils;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : heshenghao
 * @date : 17:43 2020/11/13
 */
@RestController
@RequestMapping("/validate")
@Api(tags = {"验证码接口"})
@Slf4j
public class OauthController {


    @Autowired
    private ValidateCodeService validateCodeService;

    @Resource
    TokenStore tokenStore;

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


}
