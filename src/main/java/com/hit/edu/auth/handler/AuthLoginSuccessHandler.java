package com.hit.edu.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.edu.dao.UserDetailMapper;
import com.hit.edu.util.DataResponse;
import com.hit.edu.util.JSONAuthentication;
import com.hit.edu.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 10:44
 */
@Component
@Slf4j
public class AuthLoginSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {

    @Resource
    UserDetailMapper userDetailMapper;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String username = userDetails.getUsername();

        List<String> urls = userDetailMapper.findUrlsByUserName(username);
        String token = jwtTokenUtil.generateToken(userDetails);

        this.WriteJSON(request, response, DataResponse.success(urls, token));
        log.info("登陆成功");
    }
}
