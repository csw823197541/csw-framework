package com.csw.order.controller;

import com.csw.order.OrderUrl;
import com.csw.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@RestController
public class OrderController {

    @Value("${server.port}")
    String port;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public String order(@RequestParam(value = "name", defaultValue = "csw") String name) {
        return "order " + name + " , i am from port:" + port;
    }

    @GetMapping(OrderUrl.PLACE_ORDER)
    public String placeOrder(@RequestParam(value = "name", defaultValue = "csw") String name) {
        orderService.placeOrder(name);
        return OrderUrl.PLACE_ORDER;
    }
}
