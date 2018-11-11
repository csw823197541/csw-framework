package com.csw.common.api;

import com.alibaba.druid.pool.DruidDataSource;
import com.csw.common.common.AppContext;
import com.csw.common.common.AppContextHolder;
import com.csw.common.domain.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
public class BeanRegister {

    private static Set<String> dataSourceSet = new HashSet<>();

    private static Map<String, String> dataSourceMap = new HashMap<>(); //<k,v> = <ds_pk1,ds_id>


    public static synchronized void registerDataSource(DataSource dataSource) {

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) AppContextHolder.getApplicationContext();

        if (!dataSourceSet.contains(dataSource.getId() + "_" + dataSource.getVersion())) {

            //注册dataSource
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory();
            BeanDefinitionBuilder druidDataSourceBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);


            druidDataSourceBeanDefinitionBuilder.addPropertyValue("url", dataSource.getUrl());
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("username", dataSource.getUsername());
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("password", dataSource.getPassword());
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("driverClassName", dataSource.getDriveClassName());
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("initialSize", 5);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("minIdle", 5);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("maxActive", 20);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("maxWait", 60000);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("timeBetweenEvictionRunsMillis", 60000);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("minEvictableIdleTimeMillis", 30000);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("validationQuery", "select 1 from dual");
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("testWhileIdle", true);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("testOnBorrow", false);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("testOnReturn", false);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("poolPreparedStatements", true);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("maxPoolPreparedStatementPerConnectionSize", 20);
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("filters", "stat,wall,slf4j");
            druidDataSourceBeanDefinitionBuilder.addPropertyValue("connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");

            beanDefinitionRegistry.registerBeanDefinition("dataSource" + dataSource.getId(), druidDataSourceBeanDefinitionBuilder.getBeanDefinition());
            dataSourceSet.add(dataSource.getId() + "_" + dataSource.getVersion());

            //加载sqlSessionFactory
            BeanDefinitionBuilder sqlSessionFactoryBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
            sqlSessionFactoryBeanDefinitionBuilder.addPropertyValue("mapperLocations", "classpath*:mybatis/*Mapper.xml");
            sqlSessionFactoryBeanDefinitionBuilder.addPropertyReference("dataSource", "dataSource" + dataSource.getId());
//            if(Constant.ORACLE.equalsIgnoreCase(dataSource.getType())){
//                sqlSessionFactoryBeanDefinitionBuilder.addPropertyReference("plugins", "pagePlugin");
//            }
            beanDefinitionRegistry.registerBeanDefinition("sqlSessionFactory" + dataSource.getId(), sqlSessionFactoryBeanDefinitionBuilder.getBeanDefinition());

            //添加到本地sqlSessionTemplate
            BeanDefinitionBuilder CustomSqlSessionTemplateDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CustomSqlSessionTemplate.class);
            CustomSqlSessionTemplateDefinitionBuilder.addConstructorArgReference("sqlSessionFactory" + dataSource.getId());
            beanDefinitionRegistry.registerBeanDefinition("sqlSessionTemplate" + dataSource.getId(), CustomSqlSessionTemplateDefinitionBuilder.getBeanDefinition());
        }


    }
}
