package com.cjs.example.lock.dao.primary;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjs.example.lock.model.OrderModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2019/11/11 15:33
 */

public interface OrderTTMapper {
    @Select("Select * from t_order where id = #{id}")
    OrderModel getOrderInfo(@Param("id") int id);
}
