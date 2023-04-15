package com.alone.openai.admin.annotation;

import java.lang.annotation.*;

/**
 * @description 登录校验注解，aop校验
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionValidator {
    boolean validated() default true;
}
