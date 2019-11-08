package com.cjs.example.lock.controller;

import com.cjs.example.lock.domain.request.OrderRequestVO;
import com.cjs.example.lock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChengJianSheng
 * @date 2019-07-26
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 这应该是一个业务网关服务
     */
    @PostMapping("/create")
    public String create(@RequestBody OrderRequestVO orderRequestVO,
                          @RequestHeader(name = "userid") Integer userId) {

        return orderService.save(userId, orderRequestVO.getProductId());
    }



    @PostMapping("/addProductCount")
    public boolean addProductCount(@RequestBody OrderRequestVO orderRequestVO) {

        return orderService.add(orderRequestVO.getProductId(),orderRequestVO.getProductCount());
    }

    @GetMapping("/getCount/{product}")
    public int getCount(@PathVariable("product") Integer product) {
        return orderService.getCount(product);
    }

}
