package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * privilege annotations for ops-delete
 * Created by wxcsdb88 on 2017/9/27 14:37.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecDelete {
    String title() default "删除";

    String code() default "delete";

    String privilege() default "";

    String[] depend() default {"default", "list"};
}


