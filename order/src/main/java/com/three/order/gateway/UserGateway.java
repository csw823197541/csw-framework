package com.three.order.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@Service
public class UserGateway {

    @Autowired
    UserClient userClient;

    public String findUserInfo(String name) {
        return userClient.findUserInfo(name);
    }
}
