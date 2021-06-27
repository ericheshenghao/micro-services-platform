package cn.central.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  检查requestBody注解
 * @author : heshenghao
 * @date : 13:18 2021/5/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckRequestBody {
}
