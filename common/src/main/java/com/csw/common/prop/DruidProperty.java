package com.csw.common.prop;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Getter
@Setter
public class DruidProperty {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
    private String loginUsername;
    private String loginPassword;
}
