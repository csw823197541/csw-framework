package com.csw.user.controller;

import com.csw.user.UserUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by csw on 2018/10/21.
 * Description:
 */
@RestController
public class UserController {

    @Value("${server.port}")
    String port;

    @GetMapping(UserUrl.USER_INFO)
    public String findUserInfo(@RequestParam(value = "name", defaultValue = "csw") String name) {
        return "user " + name + " , i am from port:" + port;
    }

}
