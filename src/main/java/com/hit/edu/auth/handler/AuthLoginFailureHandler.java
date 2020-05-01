package com.hit.edu.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.edu.util.DataResponse;
import com.hit.edu.util.JSONAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class AuthLoginFailureHandler extends JSONAuthentication implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String errorMsg = e.getMessage();
        System.out.println(errorMsg);
        this.WriteJSON(request, response, DataResponse.error(errorMsg));
        log.info("登陆认证失败");
    }
}
