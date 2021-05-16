package cn.central.config;

import cn.central.common.exception.DefaultExceptionAdvice;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * HttpException转换器
 * @author  heshenghao
 * @date 2018/12/22
 */
@ControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
