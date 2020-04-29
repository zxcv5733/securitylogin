package com.hit.edu.auth;

import com.hit.edu.dao.UserDetailMaper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author: Li dong.
 * @date: 2020/4/29 - 20:36
 */
@Component
@Slf4j
public class AuthUserDetailService implements UserDetailsService {

    @Resource
    UserDetailMaper userDetailMaper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!StringUtils.isEmpty(username)){
            // 加载用户基础信息
            AuthUserDetail authUserDetail = userDetailMaper.findByUserName(username);
            if (!Objects.isNull(authUserDetail)){
                // 加载用户角色列表
                List<String> roleCodes = userDetailMaper.findRoleByUserName(username);
                // 通过用户角色列表加载用户资源权限列表
                List<String> authorities = userDetailMaper.findAuthorityByRoleCodes(roleCodes);
                log.info("该:{}角色拥有的权限:{}",username,authorities);
            }
        }

        return null;
    }
}
