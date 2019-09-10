package com.three.develop.controller;

import com.three.common.vo.JsonResult;
import com.three.develop.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by csw on 2019/09/08.
 * Description:
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public JsonResult test() {
        String re = (String) testService.test("test", "helloWithoutParam");
        return JsonResult.ok("执行成功：" + re);
    }
}
