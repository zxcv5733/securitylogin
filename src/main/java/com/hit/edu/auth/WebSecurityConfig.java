package com.hit.edu.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


/**
 * @author: Li dong.
 * @date: 2020/4/29 - 20:09
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    AuthUserDetailService authUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index")
                .and().authorizeRequests()
                .antMatchers("/login.html", "/login").permitAll()
                .antMatchers("/biz1", "/biz2")
                .hasAnyAuthority("ROLE_user", "ROLE_admin")
                .antMatchers("/index").hasAnyRole("admin")
                .anyRequest().authenticated();
    }

}
