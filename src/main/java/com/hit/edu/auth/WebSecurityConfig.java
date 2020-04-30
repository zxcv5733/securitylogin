package com.hit.edu.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    AuthFailureHandler authFailureHandler;

    @Resource
    AuthSuccessHandler authSuccessHandler;

    @Resource
    JwtAuthencationTokenFilter jwtAuthencationTokenFilter;

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
//        http.csrf().disable()
//                .addFilterBefore(jwtAuthencationTokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin()
//                // 前端发起请求的路径
//                .loginProcessingUrl("/login")
//                // 指定页面提交请求的用户名、密码的参数
//                .usernameParameter("username").passwordParameter("password")
//                // 成功或失败返回给页面的响应
//                .successHandler(authSuccessHandler).failureHandler(authFailureHandler)
//                .and().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .anyRequest().access("@rbacServiceIpml.hasPermisstion(request, authentication)");

        http.csrf().disable();

        //第3步：请求权限配置
        //放行注册API请求，其它任何请求都必须经过身份验证.
        http.authorizeRequests()
                .anyRequest().access("@rbacServiceIpml.hasPermisstion(request, authentication)");

        //第4步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
        http.addFilterAt(authLoginFilter() , UsernamePasswordAuthenticationFilter.class);

        //第5步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthencationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //第7步：登录,因为使用前端发送JSON方式进行登录，所以登录模式不设置也是可以的。
        http.formLogin();
    }

    @Bean
    AuthLoginFilter authLoginFilter() throws Exception {
        // 自定义登陆请求过滤的filter
        AuthLoginFilter authLoginFilter = new AuthLoginFilter();
        // 成功返回给页面的响应
        authLoginFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        // 失败返回给页面的响应
        authLoginFilter.setAuthenticationFailureHandler(authFailureHandler);
        // 交给认证授权的管理
        authLoginFilter.setAuthenticationManager(authenticationManagerBean());

        return authLoginFilter;
    }

}
