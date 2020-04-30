package com.hit.edu.dao;


import com.hit.edu.auth.AuthUserDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @author: Li dong.
 * @date: 2020/4/29 - 21:18
 */
public interface UserDetailMapper {

    // 根据用户ID查询用户信息
    @Select("SELECT username, password, enabled\n" +
            "FROM sys_user u\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    AuthUserDetail findByUserName(@Param("userId") String userId);

    // 根据userID查询用户角色
    @Select("SELECT role_code\n" +
            "FROM sys_role r\n" +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    List<String> findRoleByUserName(@Param("userId") String userId);

    @Select({"<script>"+
            "SELECT url\n"+
            "FROM sys_menu m\n" +
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id\n"+
            "LEFT JOIN sys_role r ON r.id = rm.role_id\n"+
            "WHERE r.role_code IN \n" +
            "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close=')'>" +
            "#{roleCode} "+
            "</foreach>"+
            "</script>"})
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);

    @Select("SELECT url\n"+
            "FROM sys_menu m\n" +
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id\n"+
            "LEFT JOIN sys_role r ON r.id = rm.role_id\n"+
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN sys_user u ON u.id = ur.user_id\n" +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    List<String> findUrlsByUserName(@Param("userId")String userId);
}
