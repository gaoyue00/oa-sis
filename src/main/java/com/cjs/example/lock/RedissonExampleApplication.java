package com.cjs.example.lock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengjiansheng
 * @date 2019-07-18
 */
@SpringBootApplication
@MapperScan("com.cjs.example.lock.mapper")
public class RedissonExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonExampleApplication.class, args);
    }

}
