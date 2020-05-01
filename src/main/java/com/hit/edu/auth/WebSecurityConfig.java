package com.hit.edu.auth;

import com.hit.edu.auth.filter.AuthLoginFilter;
import com.hit.edu.auth.filter.JwtAuthencationTokenFilter;
import com.hit.edu.auth.handler.AuthJwtFailureHandler;
import com.hit.edu.auth.handler.AuthLoginFailureHandler;
import com.hit.edu.auth.handler.AuthLoginSuccessHandler;
import com.hit.edu.auth.handler.AuthLogoutSuccessHandler;
import com.hit.edu.auth.service.AuthUserDetailServiceIpml;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;


/**
 * @author: Li dong.
 * @date: 2020/4/29 - 20:09
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    AuthUserDetailServiceIpml authUserDetailServiceIpml;

    @Resource
    AuthLoginFailureHandler authLoginFailureHandler;

    @Resource
    AuthLoginSuccessHandler authLoginSuccessHandler;

    @Resource
    JwtAuthencationTokenFilter jwtAuthencationTokenFilter;

    @Resource
    AuthJwtFailureHandler authJwtFailureHandler;

    @Resource
    AuthLogoutSuccessHandler authLogoutSuccessHandler;

    /**
     * 注入Bcrypt算法加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用自定义的处理用户请求登陆的实现，使用Bcrypt算法加密
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailServiceIpml)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * SptingSecurity 主逻辑页面处理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //第1步：解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求）
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        //第3步：请求权限配置
        //放行注册API请求，其它任何请求都必须经过身份验证.
        http.authorizeRequests()
                .anyRequest().access("@rbacServiceIpml.hasPermisstion(request, authentication)");

        //第4步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
        http.addFilterAt(authLoginFilter() , UsernamePasswordAuthenticationFilter.class);

        //第5步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthencationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //第6步：处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(authJwtFailureHandler);

        //第7步：登录,因为使用前端发送JSON方式进行登录，所以登录模式不设置也是可以的。
        http.formLogin();

        //第8步：退出
        http.logout().logoutSuccessHandler(authLogoutSuccessHandler);
    }

    @Bean
    AuthLoginFilter authLoginFilter() throws Exception {
        // 自定义登陆请求过滤的filter
        AuthLoginFilter authLoginFilter = new AuthLoginFilter();
        // 成功返回给页面的响应
        authLoginFilter.setAuthenticationSuccessHandler(authLoginSuccessHandler);
        // 失败返回给页面的响应
        authLoginFilter.setAuthenticationFailureHandler(authLoginFailureHandler);
        // 交给认证授权的管理
        authLoginFilter.setAuthenticationManager(authenticationManagerBean());

        return authLoginFilter;
    }

}
