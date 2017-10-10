package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * 权限注解—方法权限
 * Created by wxcsdb88 on 2017/9/27 14:46.
 */
@Repeatable(SecFunctions.class)
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecFunction {
    String title() default "";

    String code();

    String privilege() default "";

    String[] privileges() default {""};

    String[] depend() default {"default"};
}
