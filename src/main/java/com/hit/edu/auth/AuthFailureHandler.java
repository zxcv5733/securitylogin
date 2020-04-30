package com.hit.edu.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.edu.util.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 9:23
 */
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String errorMsg = e.getMessage();
        System.out.println(errorMsg);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(DataResponse.error(e.getMessage())));
        log.info("进入登陆失败处理函数");
    }
}
