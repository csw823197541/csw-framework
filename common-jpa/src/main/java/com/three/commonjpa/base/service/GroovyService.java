package com.three.commonjpa.base.service;

import com.google.common.base.Preconditions;
import com.three.common.utils.GroovyCommonUtil1;
import com.three.commonjpa.script.entity.Script;
import com.three.commonjpa.script.repository.ScriptRepository;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by csw on 2019/09/08.
 * Description:
 */
@Slf4j
@Component
public class GroovyService {

    @Autowired
    private ScriptRepository scriptRepository;

    private Script getScriptByName(String name) {
        Preconditions.checkNotNull(name, "查找脚本，name不可以为：" + name);
        Script script = scriptRepository.findByName(name);
        Preconditions.checkNotNull(script, "查找脚本（name：" + name + ")不存在");
        return script;
    }

    /**
     * 用于调用指定Groovy脚本中的指定方法
     *
     * @param scriptName 脚本名称
     * @param methodName 方法名称
     * @param params     方法参数
     * @return Object       方法返回值
     */
    public Object exec(String scriptName, String methodName, Object... params) {
        Object res = null;

        try {

            if (GroovyCommonUtil1.getClass(scriptName) == null) {
                Script script = getScriptByName(scriptName);
                GroovyCommonUtil1.addClass(scriptName, script.getCode());
            }

            Class clazz = GroovyCommonUtil1.getClass(scriptName);
            GroovyObject instance = (GroovyObject) clazz.newInstance();

            try {
                res = instance.invokeMethod(methodName, params);
            } catch (Exception e) {
                log.warn("执行方法" + methodName + "出现异常", e);
            }

        } catch (Exception e1) {
            log.warn("加载脚本[" + scriptName + "]出现异常", e1);
        }

        return res;
    }
}
