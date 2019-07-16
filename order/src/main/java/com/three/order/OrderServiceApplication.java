package com.three.order;

import com.three.commonclient.EnableCommonClient;
import com.three.commonjpa.EnableCommonJpa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonClient
@EnableCommonJpa
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
