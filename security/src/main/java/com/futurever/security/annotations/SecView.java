package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * privilege annotations for ops-view or ops-detail
 * Created by wxcsdb88 on 2017/9/27 14:41.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecView {
    String title() default "查看";

    String code() default "view";

    String privilege() default "";

    String[] depend() default {"default", "list"};
}
