package com.csw.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.csw.common.constant.DataApiConstant;
import com.csw.common.prop.DruidProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Configuration
@Slf4j
public class DruidConfig {

    @Autowired
    private DataApiServiceConfig dataApiServiceConfig;


    @Bean
    @ConditionalOnWebApplication
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单
//        servletRegistrationBean.addInitParameter("allow", "192.168.2.25,127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        DruidProperty druid = dataApiServiceConfig.getDruid();
        servletRegistrationBean.addInitParameter("loginUsername", druid.getLoginUsername() == null ? DataApiConstant.DRUID_MONITOR_ACCOUNT : druid.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druid.getLoginPassword() == null ? DataApiConstant.DRUID_MONITOR_PASSWORD : druid.getLoginUsername());
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    //声明其为Bean实例
    //在同样的DataSource中，首先使用被标注的DataSource
    //解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() throws Exception {
        DruidProperty druid = dataApiServiceConfig.getDruid();
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(druid.getUrl());
        datasource.setUsername(druid.getUsername());
        datasource.setPassword(druid.getPassword());
        datasource.setDriverClassName(druid.getDriverClassName());

        //configuration
        datasource.setInitialSize(druid.getInitialSize());
        datasource.setMinIdle(druid.getMinIdle());
        datasource.setMaxActive(druid.getMaxActive());
        datasource.setMaxWait(druid.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(druid.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(druid.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(druid.getValidationQuery());
        datasource.setTestWhileIdle(druid.isTestWhileIdle());
        datasource.setTestOnBorrow(druid.isTestOnBorrow());
        datasource.setTestOnReturn(druid.isTestOnReturn());
        datasource.setPoolPreparedStatements(druid.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(druid.getMaxPoolPreparedStatementPerConnectionSize());
        datasource.setFilters(druid.getFilters());
        datasource.setConnectionProperties(druid.getConnectionProperties());
        return datasource;
    }
}
