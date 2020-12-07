package cn.central.oauth.controller;

import cn.central.common.model.Result;
import cn.central.oauth.service.SysUserService;
import cn.central.oauth.service.ValidateCodeService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : heshenghao
 * @date : 17:43 2020/11/13
 */
@RestController
@RequestMapping("/validate")
public class OauthController {


//    public HashMap<String, Object> user(OAuth2Authentication user){


    @Autowired
    private ValidateCodeService validateCodeService;


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
