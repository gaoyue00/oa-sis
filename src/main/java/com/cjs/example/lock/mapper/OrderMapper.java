package com.cjs.example.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjs.example.lock.model.OrderModel;
import org.apache.ibatis.annotations.Insert;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2019/11/11 15:33
 */
public interface OrderMapper extends BaseMapper<OrderModel> {

    @Insert("INSERT INTO t_order( `order_no`, `user_id`, `product_id`) " +
            "VALUES (#{orderNo},#{userId},#{productId});")
    void createOrder(OrderModel orderModel);
}
