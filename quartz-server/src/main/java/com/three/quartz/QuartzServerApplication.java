package com.three.quartz;

import com.three.commonclient.EnableCommonClient;
import com.three.commonjpa.EnableCommonJpa;
//import com.three.resource_security.EnableResourceSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonClient
@EnableCommonJpa
//@EnableResourceSecurity
public class QuartzServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzServerApplication.class, args);
    }

}
