package com.hit.edu.auth.service;

import com.hit.edu.auth.AuthUserDetail;
import com.hit.edu.dao.UserDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Li dong.
 * @date: 2020/4/29 - 20:36
 */
@Component
@Slf4j
public class AuthUserDetailServiceIpml implements UserDetailsService {

    @Resource
    UserDetailMapper userDetailMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!StringUtils.isEmpty(username)) {
            // 加载用户基础信息
            AuthUserDetail authUserDetail = userDetailMapper.findByUserName(username);
            if (authUserDetail != null) {
                // 加载用户角色列表
                List<String> roleCodes = userDetailMapper.findRoleByUserName(username);
                // 通过用户角色列表加载用户资源权限列表
                List<String> authorities = userDetailMapper.findAuthorityByRoleCodes(roleCodes);
                log.info("该:{}角色拥有的权限:{}", username, authorities);
                // 角色是一个特殊的权限，ROLE_前缀
                roleCodes = roleCodes.stream()
                        .map(rc -> "ROLE_" + rc)
                        .collect(Collectors.toList());
                // 把权限列表和角色列表整合到一个集合中
                authorities.addAll(roleCodes);
                // 把整合后的权限列表放在自定义的authUserDetail中
                authUserDetail.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",", authorities)
                ));
                return authUserDetail;
            }else {
                throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
            }
        }else {
            throw new UsernameNotFoundException(String.format("%s.用户名不能为空", username));
        }
    }
}
