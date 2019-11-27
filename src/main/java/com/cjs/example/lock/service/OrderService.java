package com.cjs.example.lock.service;

import com.cjs.example.lock.entity.TOrder;
import com.cjs.example.lock.model.OrderModel;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

/**
 * @author ChengJianSheng
 * @date 2019-07-26
 */
public interface OrderService {

    String save(Integer userId, Integer productId);

    Boolean add( Integer productId,Integer productCount);

    Integer getCount(Integer productId);

    List<OrderModel> getOrder();

    String save1(Integer id);

    String getRedisOrder(int id);
}