package com.three.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by csw on 2019/07/14.
 * Description:
 */
@RestController
@RequestMapping
public class TestController {

    @GetMapping("/sys/users/test")
    public String test() {
        int n = 3/0;
        return "hello world! /sys/users/test";
    }

    @GetMapping("/users-anon/test")
    public String test1() {
        int n = 3/0;
        return "hello world! /users-anon/test";
    }
}
