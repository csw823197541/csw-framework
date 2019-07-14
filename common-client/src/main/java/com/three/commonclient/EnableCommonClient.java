package com.three.commonclient;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.annotation.*;

/**
 * Created by csw on 2019/07/13.
 * Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
@EnableJpaAuditing
@ComponentScan("com.three.commonclient.exception")
public @interface EnableCommonClient {
}
