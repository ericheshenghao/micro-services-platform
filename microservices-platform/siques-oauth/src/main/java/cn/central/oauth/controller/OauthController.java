package cn.central.oauth.controller;

import cn.central.common.model.Result;
import cn.central.common.model.SysUser;
import cn.central.common.utils.JwtAuthenticationToken;
import cn.central.log.annotation.AuditLog;
import cn.central.log.monitor.PointUtil;
import cn.central.oauth.controller.dto.LoginDto;
import cn.central.oauth.service.SysUserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author : heshenghao
 * @date : 17:43 2020/11/13
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

//    @RequestMapping("/user")
//    public HashMap<String, Object> user(OAuth2Authentication user){
//        HashMap<String, Object> userInfo = new HashMap<>();
//        userInfo.put("user",user.getUserAuthentication().getPrincipal());
//        userInfo.put("authorities", AuthorityUtils.authorityListToSet(
//                user.getUserAuthentication().getAuthorities()));
//        return userInfo;
//    }


    @Autowired
    private SysUserService sysUserService;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){

        return Result.succeed(1);
    }


    @PostMapping("/login")
    @AuditLog(operation = "'用户登录：'+ #loginDto.getLoginCode()")
    public Result login(Authentication authentication, @RequestBody LoginDto loginDto, HttpServletRequest request){
        System.out.println(authentication);

        String loginCode = loginDto.getLoginCode();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        // 获取之前保存的验证码，与前台传来的验证码进行匹配
        String attribute = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(StrUtil.isEmpty(attribute)){
            return Result.failed("验证码已失效");
        }

        if(!captcha.equals(attribute)){
            return Result.failed("验证码错误");
        }
        // 用户信息

        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("login_code",loginCode));
        if(user ==null){
            return Result.failed("账号不存在");
        }

        if(!passwordEncoder.matches(password,user.getPassword())){

            return Result.failed("密码不正确");
        }

        if(user.getStatus()==false){
            return Result.failed("账号已锁定，请联系管理员");
        }
        // 把当前验证码舍弃
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,"");

        String accessToken = getAccessToken(loginCode, password);


        JwtAuthenticationToken token = new JwtAuthenticationToken (loginCode, "").setToken("Bearer "+accessToken);
//        List userInfo = getUserInfo(user.getUserCode());

        PointUtil.info(user.getUserCode(), "user_login", "username="+user.getUserName()+"&mobile="+user.getMobile()+"&login_code="+user.getLoginCode());

//        token.setPermissions(userInfo);

        return Result.succeed(token);
    }


    /**
     * 账号密码模式
     * @return
     */
    public String getAccessToken(String username,String password)   {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        String authorization = resource.encodeClient();
//        httpHeaders.add("Authorization", authorization);
//
//        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
//
//        param.add("grant_type", "password");
//        param.add("username", username);
//        param.add("password", password);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, httpHeaders);
//        ResponseEntity<Map> response = restTemplate.postForEntity(accessTokenUri, request , Map.class);
//        Map result = response.getBody();
//
//
//        return (String)result.get("access_token");
        return "123";
    }

    /**
     * 获取用户权限信息
     * @param userCode
     * @return
     */
//    public List  getUserInfo(String userCode){
//        if(userCode.equals(AdminConstants.ADMIN)){
//            return sysMenuService.list().stream()
//                    .filter(sysMenu -> sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms()))
//                    .map(sysMenu -> sysMenu.getPerms()).collect(Collectors.toList());
//        }
//        return sysUserService.findPermission(userCode).stream().collect(Collectors.toList());
//    }

    /**
     * 验证码接口
     * @param response
     * @param request
     * @throws IOException
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {

        CaptchaUtil.setHeader(response);
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(100, 35, 4);
        // 设置类型：字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        // 保存验证码

        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,gifCaptcha.text().toLowerCase());
        // 输出图片流
        gifCaptcha.out(response.getOutputStream());

    }

}
