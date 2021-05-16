package cn.central.common.resolver;

import cn.central.common.annotation.LoginUser;
import cn.central.common.feign.AuthService;


import cn.central.common.model.SysUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 上下文中获取用户信息
 *
 * @author he
 * @date
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    private AuthService userService;

    public TokenArgumentResolver(AuthService userService) {
        this.userService = userService;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        boolean isFull = loginUser.isFull();
        SysUser result = new SysUser();
        // 已认证过的状态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null && !(authentication instanceof AnonymousAuthenticationToken))
        {
            String userCode = authentication.getName();
            if (isFull) {
                result = userService.selectByUserCode(userCode);
            } else {

                result.setUserCode(userCode);
            }

        }

        return result;
    }
}
