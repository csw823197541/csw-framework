package com.csw.common.common;

import org.springframework.context.ApplicationContext;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
public class AppContextHolder {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    static void setApplicationContext(ApplicationContext applicationContext) {
        AppContextHolder.applicationContext = applicationContext;

    }
}
