package com.cjs.example.lock.constant;

import java.lang.annotation.*;

/**
 * 根据key限制接口同时只允许一个请求执行<br>
 * key应为前缀加业务主键 保证唯一<br>
 * key支持SqEL 例如: "#orderId","#request.orderId","'ADD_ORDER_' + #req.orderId"
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLock {

    String key();
}
