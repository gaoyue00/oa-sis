package com.cjs.example.lock.controller;

import com.cjs.example.lock.domain.request.OrderRequestVO;
import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.service.OrderService;
import com.cjs.example.lock.service.OrderTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChengJianSheng
 * @date 2019-07-26
 */
@RestController
@RequestMapping("/ordeTT")
public class OrderTTController {

    @Autowired
    private OrderTTService orderTTService;

    @GetMapping("/getOrderInfo")
    public OrderModel getOrderInfo() {
        return orderTTService.getOrderInfo();
    }

}
