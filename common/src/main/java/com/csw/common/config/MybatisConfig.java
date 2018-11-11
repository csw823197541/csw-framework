package com.csw.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Configuration
@MapperScan("com.csw.common.dao")
public class MybatisConfig {

}
