package com.cjs.example.lock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengjiansheng
 * @date 2019-07-18
 */
@Configuration
@SpringBootApplication
@EnableCaching  //开启缓存
@MapperScan("com.cjs.example.lock.mapper")
public class RedissonExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonExampleApplication.class, args);
    }

}
