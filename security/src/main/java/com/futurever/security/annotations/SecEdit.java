package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * privilege annotations for ops-edit
 * Created by wxcsdb88 on 2017/9/27 14:39.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecEdit {
    String title() default "编辑";

    String code() default "edit";

    String privilege() default "";

    String[] depend() default {"default", "list"};
}
