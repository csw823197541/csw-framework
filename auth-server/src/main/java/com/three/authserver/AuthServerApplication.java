package com.three.authserver;

import com.three.commonclient.EnableCommonClient;
import com.three.commonjpa.EnableCommonJpa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonClient
@EnableCommonJpa
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
