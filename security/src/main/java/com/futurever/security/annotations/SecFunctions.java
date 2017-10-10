package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * 权限注解—方法权限
 * Created by wxcsdb88 on 2017/9/27 14:45.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecFunctions {
    SecFunction[] value();
}
