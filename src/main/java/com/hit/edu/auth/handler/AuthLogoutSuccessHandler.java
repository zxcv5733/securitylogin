package com.hit.edu.auth.handler;

import com.hit.edu.util.DataResponse;
import com.hit.edu.util.JSONAuthentication;
import com.hit.edu.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Li dong.
 * @date: 2020/5/1 - 16:22
 * 成功退出处理器
 */
@Component
@Slf4j
public class AuthLogoutSuccessHandler extends JSONAuthentication implements LogoutSuccessHandler {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isEmpty(token)) {
            SecurityContextHolder.clearContext();
        }
        this.WriteJSON(request,response, DataResponse.success("退出成功"));
        log.info("退出成功");
    }
}
