package com.hit.edu.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author: Li dong.
 * @date: 2020/4/29 - 21:32
 */
@Data
public class AuthUserDetail implements UserDetails {
    // 密码
    private String password;
    // 用户名
    private String username;
    // 是否没过期
    private boolean accountNonExpired;
    // 是否没被锁定
    private boolean accountNonLocked;
    // 凭证是否没过期
    private boolean credentialsNonExpired;
    // 账号是否可用
    private boolean enabled;
    // 用户的权限集合
    private Collection<? extends GrantedAuthority> Authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
