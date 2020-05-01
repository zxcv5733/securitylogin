package com.hit.edu.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Li dong.
 * @date: 2020/4/26 - 10:13
 */
@RestController
public class LoginController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/syslog", method = RequestMethod.GET)
    public String showOrder(){
        return "syslog";
    }

    @RequestMapping(value = "/sysuser", method = RequestMethod.GET)
    public String addOrder(){
        return "sysuser";
    }

    @RequestMapping(value = "/biz1", method = RequestMethod.GET)
    public String showbiz1(){
        return "biz1";
    }

    @RequestMapping(value = "/biz2", method = RequestMethod.GET)
    public String showbiz2(){
        return "biz2";
    }

}
