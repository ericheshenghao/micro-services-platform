package cn.central.common.ribbon.filter;

import cn.central.common.constant.CommonConstant;
import cn.central.common.constant.ConfigConstants;
import cn.central.common.context.LbIsolationContextHolder;
import cn.hutool.core.util.StrUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 负载均衡隔离规则过滤器
 *
 * @date 2019/9/15
 */
@ConditionalOnClass(Filter.class)
public class LbIsolationFilter extends OncePerRequestFilter {
    @Value("${" + ConfigConstants.CONFIG_RIBBON_ISOLATION_ENABLED + ":false}")
    private boolean enableIsolation;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !enableIsolation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String version = request.getHeader(CommonConstant.Z_L_T_VERSION);
            if(StrUtil.isNotEmpty(version)){
                LbIsolationContextHolder.setVersion(version);
            }

            filterChain.doFilter(request, response);
        } finally {
            LbIsolationContextHolder.clear();
        }
    }
}
