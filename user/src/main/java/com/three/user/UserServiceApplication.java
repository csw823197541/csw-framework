package com.three.user;

import com.three.commonjpa.EnableCommonJpa;
import com.three.commonclient.EnableCommonClient;
import com.three.resource_security.EnableResourceSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonClient
@EnableCommonJpa
@EnableResourceSecurity
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
