package com.three.order.gateway;

import org.springframework.stereotype.Component;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@Component
public class UserClientHystrix implements UserClient {

    @Override
    public String findUserInfo(String name) {
        return UserUrl.SERVICE_NAME + " call failed!";
    }
}
