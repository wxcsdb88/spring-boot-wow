package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * privilege annotations for ops-list
 * Created by wxcsdb88 on 2017/9/27 14:39.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecList {
    String title() default "浏览";

    String code() default "list";

    String privilege() default "";

    String[] depend() default {"default"};
}
