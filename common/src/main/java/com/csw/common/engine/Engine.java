package com.csw.common.engine;

import com.csw.common.domain.DataSource;
import com.csw.common.domain.Script;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
public interface Engine {

    List<Object> execute(SqlSessionTemplate sqlSessionTemplate, Map<Object, Object> params, Script script, DataSource dataSource) throws Exception;

}
