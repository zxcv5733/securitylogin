package com.hit.edu.auth;

import com.hit.edu.exception.AuthException;
import com.hit.edu.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 16:01
 */
@Component
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(jwtTokenUtil.getHeader());
        System.out.println(jwtToken);
        if (!StringUtils.isEmpty(jwtToken)){
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            System.out.println(username);
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
            if (!jwtTokenUtil.isTokenExpired(jwtToken)){
                new Throwable("令牌已过期，请重新登录");
            }
            if (!StringUtils.isEmpty(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) ){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)){
                    System.out.println("进行授权");
                    // 给使用JWT令牌的用户进行授权
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        System.out.println("进入自己的过滤链");
        filterChain.doFilter(request, response);
    }
}
