package com.three.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by csw on 2019/07/16.
 * Description:
 */
@Configuration
@ComponentScan("com.three.user")
@EnableJpaRepositories("com.three.user")
@EntityScan("com.three.user")
public class ComponentScanConfig {
}
