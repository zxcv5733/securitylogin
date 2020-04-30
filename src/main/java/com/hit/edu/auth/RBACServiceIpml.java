package com.hit.edu.auth;

import com.hit.edu.dao.UserDetailMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 13:15
 */
@Component("rbacServiceIpml")
public class RBACServiceIpml {
    @Resource
    UserDetailMapper userDetailMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 判断用户是否具有资源访问权限
    */
    public boolean hasPermisstion (HttpServletRequest request, Authentication authentication){


        Object principal = authentication.getPrincipal();
        System.out.println("进入动态加载");
        System.out.println(principal);
        if (principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            List<String> urls = userDetailMapper.findUrlsByUserName(username);
            System.out.println(urls);
            boolean result = urls.stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
            return result;
        }


        return false;
    }
}
