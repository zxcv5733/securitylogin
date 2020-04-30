package com.hit.edu.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 17:28
 * 自定义异常类，继承AuthenticationException
 * 在有throws AuthenticationException方法上捕获
 * 方式：throw new AuthException
 */
public class AuthException extends AuthenticationException {

    public AuthException(String msg) {
        super(msg);
    }
}
