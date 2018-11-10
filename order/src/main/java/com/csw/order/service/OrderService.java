package com.csw.order.service;

import com.csw.order.gateway.UserGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    UserGateway userGateway;

    public void placeOrder(String name) {
        String userInfo = userGateway.findUserInfo(name);
        logger.info(String.format("查找到用户信息：%s", userInfo));
    }
}
