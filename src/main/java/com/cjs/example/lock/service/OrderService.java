package com.cjs.example.lock.service;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author ChengJianSheng
 * @date 2019-07-26
 */
public interface OrderService {

    String save(Integer userId, Integer productId);

    Boolean add( Integer productId,Integer productCount);

    Integer getCount(Integer productId);

}