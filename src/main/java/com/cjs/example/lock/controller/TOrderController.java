package com.cjs.example.lock.controller;


import com.cjs.example.lock.constant.RequestLock;
import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.service.ITOrderService;
import com.cjs.example.lock.service.OrderService;
import com.cjs.example.lock.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Tuple;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gy
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/t-order")
@Slf4j
public class TOrderController {

    @Autowired
    private OrderService orderService;

    @Resource
    private RedisService redisService;


    @PostMapping("/getOrder")
    @RequestLock(key = "'orderLock_' + #id")
    public String getOrder(@RequestParam("id") Integer id) {
        String s = orderService.save1(id);
        System.out.println("返回结果"+id);
        return s;
    }



    public void getTestOrder() {
        log.info("进入getTestOrder");
        while(true) {
            Set  scoreSet = redisService.zrangeWithScores("Test_OrderId", 0, 1);
            if (scoreSet.size() == 0 || scoreSet == null  ) {

                System.out.println("当前没有等待的任务");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            }
            DefaultTypedTuple defaultTypedTuple = (DefaultTypedTuple) scoreSet.toArray()[0];
            int score = defaultTypedTuple.getScore().intValue();
            String test_orderId = defaultTypedTuple.getValue().toString();

            Calendar cal = Calendar.getInstance();
            int nowSecond = (int) (cal.getTimeInMillis() / 1000);
            if(nowSecond >= score){
                redisService.zremove("Test_OrderId",test_orderId);
                System.out.println(System.currentTimeMillis() +"ms:redis消费了一个任务：消费的订单OrderId为"+test_orderId);

            }
        }
    }

    /**
     * redis 时间延时消费
     * @return
     */
    @GetMapping("/getTestOrder1")
    public String getTestOrder1() {
        //生产者,生成5个订单放进去
            for(int i=0;i<5;i++){
                //延迟3秒
                Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.SECOND, 15);
                int second3later = (int) (cal1.getTimeInMillis() / 1000);
                log.info(second3later+"=============");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisService.zAdd("Test_OrderId","OID0000001"+i,second3later);
                System.out.println(System.currentTimeMillis()+"ms:redis生成了一个订单任务：订单ID为"+"OID0000001"+i);

            }
        return "";
    }

    @RequestMapping(value = "/getRedisOrder")
    public String getOrder(@RequestParam(value = "id") int id) {

        return orderService.getRedisOrder(id);
    }
}
