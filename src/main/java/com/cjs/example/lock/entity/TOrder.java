package com.cjs.example.lock.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gy
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private Integer id;
    /**
     * 订单id
     */
    private String orderNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更改时间
     */
    private LocalDateTime updateTime;


}
