package cn.central.common.exception;

import cn.central.common.model.BasicResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

/**
 * 异常通用处理
 *
 * @author he
 */
@ResponseBody
@Slf4j
public class DefaultExceptionAdvice {
    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({IllegalArgumentException.class})
    public BasicResponse badRequestException(IllegalArgumentException e) {
        return defHandler("参数解析失败", e, HttpStatus.BAD_REQUEST.value());
    }

    /**
     * {@link UnauthorizedUserException 异常处理}
     * 返回状态码:401
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({UnauthorizedUserException.class})
    public BasicResponse unAuthorizedException(IllegalArgumentException e) {
        return defHandler("未授权", e, HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * AccessDeniedException异常处理返回json
     * 返回状态码:403
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AccessDeniedException.class})
    public BasicResponse badMethodExpressException(AccessDeniedException e) {
        return defHandler("没有权限请求当前方法", e, HttpStatus.FORBIDDEN.value());
    }

    /**
     * 返回状态码:405
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BasicResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return defHandler("不支持当前请求方法", e, HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    /**
     * 返回状态码:415
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public BasicResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return defHandler("不支持当前媒体类型", e, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    /**
     * SQLException sql异常处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SQLException.class})
    public BasicResponse handleSQLException(SQLException e) {
        return defHandler("服务运行SQLException异常", e, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public BasicResponse handleException(BusinessException e) {
        return defHandler("业务异常", e, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * IdempotencyException 幂等性异常
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IdempotencyException.class)
    public BasicResponse handleException(IdempotencyException e) {
        return BasicResponse.failed(e.getMessage(), HttpStatus.OK.value());
    }

    /**
     * 所有异常统一处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public BasicResponse handleException(Exception e) {
        return defHandler(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private BasicResponse defHandler(String msg, Exception e, Integer code) {
        log.error(msg, e);
        return BasicResponse.failed(msg, code);
    }
}
