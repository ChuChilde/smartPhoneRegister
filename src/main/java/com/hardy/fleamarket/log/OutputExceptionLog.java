package com.hardy.fleamarket.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OutputExceptionLog {
    String message() default "";
}
