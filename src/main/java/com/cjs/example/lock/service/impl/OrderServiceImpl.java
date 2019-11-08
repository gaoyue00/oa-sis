package com.cjs.example.lock.service.impl;

import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.repository.OrderRepository;
import com.cjs.example.lock.service.OrderService;
import com.cjs.example.lock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ChengJianSheng
 * @date 2019-07-30
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StockService stockService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 乐观锁
     */
    @Override
    public String save(Integer userId, Integer productId) {
        int stock = stockService.getByProduct(productId);

        if (stock <= 0) {
            return "库存不足！！！";
        }
        //  如果不加锁，必然超卖
        RLock lock = redissonClient.getLock("stock:" + productId);
        try {
            Boolean isLock = lock.tryLock(1,180,TimeUnit.SECONDS);
            if(!isLock){
                return "有其他人在操作";
            }
//            lock.lock(10, TimeUnit.SECONDS);
            if (stockService.decrease(productId)) {
                addOrder(userId+":"+Thread.currentThread().getName(),productId);
            }
        } catch (Exception ex) {
            log.error("下单失败", ex);
        } finally {
           if (lock.isHeldByCurrentThread()){
               lock.unlock();
           }else {
               return "操作超时返回";
           }

        }

        return Thread.currentThread().getName()+"操作成功！！！";
    }

    @Override
    public Boolean add(Integer productId,Integer productCount) {
        return stockService.addProductCount(productId,productCount);
    }

    @Override
    public Integer getCount(Integer productId) {
        return stockService.getCount(productId);
    }


    public String addOrder(String userId, Integer productId){
        String orderNo = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setProductId(productId);
        orderModel.setOrderNo(orderNo);
        Date now = new Date();
        orderModel.setCreateTime(now);
        orderModel.setUpdateTime(now);
        orderRepository.save(orderModel);

        return orderNo;
    }



}
