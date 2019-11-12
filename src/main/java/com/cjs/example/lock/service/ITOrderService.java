package com.cjs.example.lock.service;

import com.cjs.example.lock.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gy
 * @since 2019-11-12
 */
public interface ITOrderService extends IService<TOrder> {



    String save(Integer userId, Integer productId);

    Boolean add( Integer productId,Integer productCount);

    Integer getCount(Integer productId);
}
