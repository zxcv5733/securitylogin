package com.hit.edu.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.edu.util.DataResponse;
import com.hit.edu.util.JSONAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li dong.
 * @date: 2020/5/1 - 8:17
 *
 * 身份校验失败处理器，如 token 错误
 */
@Component
@Slf4j
public class AuthJwtFailureHandler extends JSONAuthentication implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        this.WriteJSON(request, response, DataResponse.error(e.getMessage()));
        log.info("token失效或者不正确");
    }
}
