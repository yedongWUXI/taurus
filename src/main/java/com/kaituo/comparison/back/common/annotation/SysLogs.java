package com.kaituo.comparison.back.common.annotation;

import java.lang.annotation.*;

/**
 *
 *
 * 系统日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogs {

    String value();

}
