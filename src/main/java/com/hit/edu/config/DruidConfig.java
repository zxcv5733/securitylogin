package com.hit.edu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


/**
 * @author: Li dong.
 * @date: 2020/4/29 - 19:33
 */
@Configuration
public class DruidConfig {
    /**
     * ConfigurationProperties:添加配置文件中属性以spring.datasource开头的属性
     * Bean:添加到spring的容器中
     * @return 自定义的druid数据库连接池
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }


}
