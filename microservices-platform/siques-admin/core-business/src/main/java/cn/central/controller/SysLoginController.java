package cn.central.controller;


import cn.central.common.config.OauthResourceTokenConfig;
import cn.central.common.constant.AdminConstants;
import cn.central.common.model.Result;
import cn.central.common.model.SysUser;
import cn.central.common.utils.JwtAuthenticationToken;
import cn.central.controller.dto.LoginDto;
import cn.central.log.annotation.AuditLog;
import cn.central.log.monitor.PointUtil;
import cn.central.service.SysMenuService;
import cn.central.service.SysUserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/pub/")
@Api(description = "SysLoginController", tags = {"认证相关结构"})
public class SysLoginController {
    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){

        return Result.succeed(1);
    }


    @PostMapping("/login")
    @AuditLog(operation = "'用户登录：'+ #loginDto.getLoginCode()")
    public Result login(@RequestBody LoginDto loginDto, HttpServletRequest request){
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
        List userInfo = getUserInfo(user.getUserCode());

        PointUtil.info(user.getUserCode(), "user_login", "username="+user.getUserName()+"&mobile="+user.getMobile()+"&login_code="+user.getLoginCode());

        token.setPermissions(userInfo);

        return Result.succeed(token);
    }

    @Autowired
    OauthResourceTokenConfig resource;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;


    @Value("${security.oauth2.client.user-info-uri}")
    private String userInfoUri;
    /**
     * 账号密码模式
     * @return
     */
    public String getAccessToken(String username,String password)   {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String authorization = resource.encodeClient();
        httpHeaders.add("Authorization", authorization);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        param.add("grant_type", "password");
        param.add("username", username);
        param.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, httpHeaders);
        ResponseEntity<Map> response = restTemplate.postForEntity(accessTokenUri, request , Map.class);
        Map result = response.getBody();


        return (String)result.get("access_token");
    }

    /**
     * 获取用户权限信息
     * @param userCode
     * @return
     */
    public List  getUserInfo(String userCode){
        if(userCode.equals(AdminConstants.ADMIN)){
            return sysMenuService.list().stream()
                    .filter(sysMenu -> sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms()))
                    .map(sysMenu -> sysMenu.getPerms()).collect(Collectors.toList());
        }
      return sysUserService.findPermission(userCode).stream().collect(Collectors.toList());
    }

    /**
     * 验证码接口
     * @param response
     * @param request
     * @throws IOException
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {


        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成验证
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        // 保存到session 或者redis
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,text);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        IOUtils.closeQuietly(outputStream);
    }

}
