package cn.central.common.filter;

import cn.central.common.constant.CommonConstant;
import cn.central.common.constant.SecurityConstants;
import cn.central.common.context.TenantContextHolder;
import cn.hutool.core.util.StrUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 租户过滤器
 *
 * @date 2019/9/15
 */
@ConditionalOnClass(Filter.class)
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            //优先获取请求参数中的tenantId值
            String tenantId = request.getParameter(CommonConstant.TENANT_ID_PARAM);
            if (StrUtil.isEmpty(tenantId)) {
                tenantId = request.getHeader(SecurityConstants.TENANT_HEADER);
            }

            TenantContextHolder.setTenant("123");
            //保存租户id
            if (StrUtil.isNotEmpty(tenantId)) {
                TenantContextHolder.setTenant(tenantId);
            }

            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
