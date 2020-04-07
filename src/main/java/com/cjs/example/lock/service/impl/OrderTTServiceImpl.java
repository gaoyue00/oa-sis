package com.cjs.example.lock.service.impl;


import com.cjs.example.lock.dao.primary.OrderMapper;
import com.cjs.example.lock.dao.primary.OrderTTMapper;
import com.cjs.example.lock.entity.TOrder;
import com.cjs.example.lock.mapper.TOrderMapper;
import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.repository.OrderRepository;
import com.cjs.example.lock.service.OrderService;
import com.cjs.example.lock.service.OrderTTService;
import com.cjs.example.lock.service.RedisService;
import com.cjs.example.lock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ChengJianSheng
 * @date 2019-07-30
 */
@Component
public class OrderTTServiceImpl implements OrderTTService {



    @Autowired
    private OrderTTMapper orderTTMapper;


    @Override
    public OrderModel getOrderInfo() {
        OrderModel orderInfo = orderTTMapper.getOrderInfo(29);
        System.out.println(orderInfo);
        return orderInfo;
    }

}
