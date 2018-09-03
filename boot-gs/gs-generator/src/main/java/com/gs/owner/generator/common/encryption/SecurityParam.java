package com.gs.owner.generator.common.encryption;

import java.lang.annotation.*;

/**
 * description: responseBody 和 responseBody 加密解密注解类
 *
 * @auth guoshuai
 * @since 2018/9/3
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SecurityParam {

    /**
     * 入参默认加密
     */
    boolean request() default true;

    /**
     * 响应默认加密
     */
    boolean response() default false;

}
