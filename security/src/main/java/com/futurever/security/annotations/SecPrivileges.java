package com.futurever.security.annotations;

import java.lang.annotation.*;

/**
 * Created by wxcsdb88 on 2017/9/27 14:56.
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecPrivileges {
    SecPrivilege[] value();
}
