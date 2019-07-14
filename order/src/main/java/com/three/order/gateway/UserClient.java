package com.three.order.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@FeignClient(value = UserUrl.SERVICE_NAME, fallback = UserClientHystrix.class)
public interface UserClient {

    @GetMapping(UserUrl.USER_INFO)
    String findUserInfo(@RequestParam(value = "name", defaultValue = "csw") String name);
}
