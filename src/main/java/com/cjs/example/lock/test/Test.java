package com.cjs.example.lock.test;

import com.cjs.example.lock.config.AppConfig;
import com.cjs.example.lock.service.OrderTTService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/3/13 17:23
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        annotationConfigApplicationContext.getBean(OrderTTService.class).getOrderInfo();
    }
}
