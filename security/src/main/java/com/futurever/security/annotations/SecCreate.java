package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * privilege annotations for ops-create
 * Created by wxcsdb88 on 2017/9/27 14:18.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecCreate {
    String title() default "添加";

    String code() default "create";

    String privilege() default "";

    String[] depend() default {"default", "list"};
}
