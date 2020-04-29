package com.hit.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Li dong.
 * @date: 2020/4/29 - 19:33
 */
@MapperScan("com.hit.edu.dao")
@SpringBootApplication
public class SecurityLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityLoginApplication.class, args);
    }
}
