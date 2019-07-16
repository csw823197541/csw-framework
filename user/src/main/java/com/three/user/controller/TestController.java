package com.three.user.controller;

import com.three.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by csw on 2019/07/14.
 * Description:
 */
@Slf4j
@RestController
@RequestMapping
public class TestController {

    @GetMapping("/sys/users/test")
    public JsonResult test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("test:{}", authentication.getName());
        return JsonResult.ok().put("data", authentication);
//        return JsonResult.ok().put("data", "/sys/users/test");
    }

    @GetMapping("/users-anon/test")
    public String test1() {
        return "hello world! /users-anon/test";
    }
}
