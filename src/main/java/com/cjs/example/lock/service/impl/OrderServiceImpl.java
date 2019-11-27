package com.cjs.example.lock.service.impl;


import com.cjs.example.lock.dao.primary.OrderMapper;
import com.cjs.example.lock.entity.TOrder;

import com.cjs.example.lock.mapper.TOrderMapper;
import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.repository.OrderRepository;
import com.cjs.example.lock.service.OrderService;
import com.cjs.example.lock.service.RedisService;
import com.cjs.example.lock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TOrderMapper tOrderMapper;

    @Resource
    private RedisService redisService;



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
//            Boolean isLock = lock.tryLock(1,180,TimeUnit.SECONDS);

            Boolean isLock = lock.isLocked();
            if(!isLock){
                return "有其他人在操作";
            }
            lock.lock(60,TimeUnit.SECONDS);
//            lock.lock(10, TimeUnit.SECONDS);
            if (stockService.decrease(productId)) {
                addOrder(userId+":"+Thread.currentThread().getName(),productId);
            }
        } catch (Exception ex) {
            log.error("下单失败", ex);
            return  "下单失败";
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

    @Override
    public List<OrderModel> getOrder() {
        LocalDateTime nowTime = LocalDateTime.now();
        List<OrderModel> orderModel = null;

         orderModel = orderMapper.getOrder1(nowTime);

        orderModel.addAll(orderMapper.getOrder(nowTime));
        System.out.println(orderModel);
        return orderModel;
    }

    @Override
    public String save1(Integer id) {
            if(this.save(id) == 0){
                return "抢购无效";
            }
        return "抢购成功";
    }

    @Cacheable(value = "Test_OrderId" ,key = "#root.methodName+'['+#id+']'")
    @Override
    public String getRedisOrder( int id) {
        System.out.println("进入缓存查询"+id);
        Set setId = redisService.zrangeWithScores("Test_OrderId", 0, 10);
        DefaultTypedTuple defaultTypedTuple = (DefaultTypedTuple) setId.toArray()[0];

        return  defaultTypedTuple.getValue().toString();
    }

    public String addOrder(String userId, Integer productId){
        String orderNo = UUID.randomUUID().toString().replace("-", "").toUpperCase();
//        OrderModel orderModel = new OrderModel();
        TOrder orderModel = new TOrder();
        orderModel.setUserId(userId);
        orderModel.setProductId(productId);
        orderModel.setOrderNo(orderNo);

//        orderRepository.save(orderModel);
//        orderMapper.createOrder(orderModel);
//        orderMapper.insert(orderModel);
        tOrderMapper.insert(orderModel);
        return orderNo;
    }

    private int save(Integer id){
        TOrder tOrder = tOrderMapper.selectById(id);
        if(tOrder == null || tOrder.getStatus() == 1){
            return 0;
        }else {
            tOrder.setStatus(1);
            tOrderMapper.updateById(tOrder);
            log.info("抢购成功,状态已更改"+Thread.currentThread().getName());
            return  1;
        }
    }



}
