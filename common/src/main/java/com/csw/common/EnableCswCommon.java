package com.csw.common;

import com.csw.common.config.ComponentScanConfig;
import com.csw.common.config.MybatisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ComponentScanConfig.class, MybatisConfig.class})
public @interface EnableCswCommon {

}
