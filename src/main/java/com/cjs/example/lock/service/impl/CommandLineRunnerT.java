package com.cjs.example.lock.service.impl;

import com.cjs.example.lock.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Set;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2019/11/26 17:19
 */
@Slf4j
@EnableScheduling
@Component
public class CommandLineRunnerT  {

    @Resource
    private RedisService redisService;


    @Scheduled(fixedRate = 3000)
    public void getOrder() throws Exception {
        log.info("进入getTestOrder");
            Set scoreSet = redisService.zrangeWithScores("Test_OrderId", 0, 1);
            if (scoreSet.size() == 0 || scoreSet == null  ) {

                System.out.println("当前没有等待的任务");
                return;
            }
            DefaultTypedTuple defaultTypedTuple = (DefaultTypedTuple) scoreSet.toArray()[0];
            int score = defaultTypedTuple.getScore().intValue();
            String test_orderId = defaultTypedTuple.getValue().toString();

            Calendar cal = Calendar.getInstance();
            int nowSecond = (int) (cal.getTimeInMillis() / 1000);
            if(nowSecond >= score){
                Long num  = redisService.zremove("Test_OrderId", test_orderId);
                if( num != null && num>0) {
                    System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务：消费的订单OrderId为" + test_orderId);
                }
            }
    }
}
