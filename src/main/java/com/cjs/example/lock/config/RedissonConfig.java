package com.cjs.example.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChengJianSheng
 * @date 2019-07-26
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://60.205.106.190:6379";
        String password = "oasisadmin";
        config.useSingleServer().setAddress(address).setPassword(password)
                .setConnectionMinimumIdleSize(5);
        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }

}
