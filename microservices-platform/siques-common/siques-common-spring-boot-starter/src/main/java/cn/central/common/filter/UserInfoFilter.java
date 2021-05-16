package cn.central.common.filter;

import cn.central.common.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : heshenghao
 * @date : 22:45 2021/5/9
 */
@ConditionalOnClass(Filter.class)
public class UserInfoFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);
    }
}
