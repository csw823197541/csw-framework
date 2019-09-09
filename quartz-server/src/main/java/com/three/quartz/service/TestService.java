package com.three.quartz.service;

import com.three.commonjpa.base.service.GroovyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by csw on 2019/09/09.
 * Description:
 */
@Service
public class TestService {

    @Autowired
    private GroovyService groovyService;

    public Object test(String test, String helloWithoutParam) {
        return groovyService.exec(test, helloWithoutParam);
    }
}
