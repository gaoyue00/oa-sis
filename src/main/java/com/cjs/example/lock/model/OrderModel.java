package com.cjs.example.lock.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ChengJianSheng
 * @date 2019-07-30
 */
@Data
@Entity
@TableName(value = "t_order")
public class OrderModel implements Serializable {

    @Id
    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private Integer id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
