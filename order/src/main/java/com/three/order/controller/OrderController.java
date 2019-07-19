package com.three.order.controller;

import com.three.common.vo.JsonResult;
import com.three.order.service.OrderService;
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
    public JsonResult order(@RequestParam(value = "name", defaultValue = "csw") String name) {
        return JsonResult.ok("order " + name + " , i am from port:" + port);
    }
}
