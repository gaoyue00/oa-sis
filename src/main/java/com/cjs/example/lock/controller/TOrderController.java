package com.cjs.example.lock.controller;


import com.cjs.example.lock.constant.RequestLock;
import com.cjs.example.lock.model.OrderModel;
import com.cjs.example.lock.service.ITOrderService;
import com.cjs.example.lock.service.OrderService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gy
 * @since 2019-11-12
 */
@RestController
@RequestMapping("/t-order")
public class TOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/getOrder")
    @RequestLock(key = "'orderLock_' + #id")
    public String getOrder(@RequestParam("id") Integer id) {
        return orderService.save1(id);
    }

}
