package cn.siques.job.core.handler.annotation;

import java.lang.annotation.*;

/**
 * @author : heshenghao
 * @date : 15:44 2021/5/16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JobHandler {

    String value() default "";

}