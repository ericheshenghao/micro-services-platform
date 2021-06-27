package cn.central.common.aspect;

import cn.central.common.annotation.CheckRequestBody;
import cn.central.common.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : heshenghao
 * @date : 13:19 2021/5/23
 */
@Aspect
@ConditionalOnClass(BindingResult.class)
public class CheckRequestBodyAspect {

    @Resource
    private ObjectMapper objectMapper;

    @Around("@within(CheckRequestBody) || @annotation(CheckRequestBody)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, CheckRequestBody CheckRequestBody) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 查询绑定值中是否有错误
        List<Object> collect = Arrays.stream(args).filter(o -> o instanceof BindingResult).collect(Collectors.toList());
        List<Object> responses = Arrays.stream(args).filter(o -> o instanceof HttpServletResponse).collect(Collectors.toList());
        for (Object obj : collect) {
            BindingResult result = (BindingResult) obj;
            if (result.hasErrors()) {
                List<ObjectError> allErrors = result.getAllErrors();
                String reduce = allErrors.stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining());
                HttpServletResponse res = (HttpServletResponse) responses.get(0);
                ResponseUtil.responseFailed(objectMapper, res, reduce, 0);
                return null;
            }
        }

        return joinPoint.proceed(args);
    }
}
