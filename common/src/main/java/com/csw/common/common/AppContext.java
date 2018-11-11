package com.csw.common.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Component("appContext")
public class AppContext implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHolder.setApplicationContext(applicationContext);
    }

}
