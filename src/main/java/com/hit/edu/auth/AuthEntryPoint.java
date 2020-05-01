package com.hit.edu.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.edu.util.DataResponse;
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
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("token失效或者不正确");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(DataResponse.error(e.getMessage())));
    }
}
