package com.hit.edu.service;

/**
 * @author: Li dong.
 * @date: 2020/4/30 - 19:40
 */
public interface UserService {
   boolean checkLogin(String username, String password) throws Exception;
}
