package com.csw.common.config;

import com.csw.common.prop.DataApiProperty;
import com.csw.common.prop.DruidProperty;
import com.csw.common.prop.MyBatisProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "csw")
@PropertySources(value = {
        @PropertySource(value = {"classpath:csw-dataApi.properties"}, encoding = "UTF-8", ignoreResourceNotFound = true)
})
@Getter
@Setter
public class DataApiServiceConfig {

    private DruidProperty druid;

    private MyBatisProperty mybatis;

    private DataApiProperty dataApi;
}
