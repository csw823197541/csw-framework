package com.three.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    public void placeOrder(String name) {
        logger.info(String.format("查找到用户信息：%s", ""));
    }
}
