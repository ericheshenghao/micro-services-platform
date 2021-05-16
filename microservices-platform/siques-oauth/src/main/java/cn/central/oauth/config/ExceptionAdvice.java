package cn.central.oauth.config;

import cn.central.common.exception.DefaultExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 对http访问异常的处理
 * @author  heshenghao
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
