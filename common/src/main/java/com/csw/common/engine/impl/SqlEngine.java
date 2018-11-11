package com.csw.common.engine.impl;

import com.csw.common.api.CustomSqlSessionTemplate;
import com.csw.common.constant.DataApiConstant;
import com.csw.common.domain.DataSource;
import com.csw.common.domain.Script;
import com.csw.common.engine.Engine;
import com.csw.common.exception.BusinessException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
@Component
public class SqlEngine implements Engine {

    @Override
    public List<Object> execute(SqlSessionTemplate sqlSessionTemplate, Map<Object, Object> params, Script script, DataSource dataSource) throws Exception {
        List<Object> resultList = new ArrayList<>();
        String version = script.getVersion().toString();
        String dsId = dataSource == null ? "default" : dataSource.getKey();
        CustomSqlSessionTemplate customerSqlSessionTemplate = (CustomSqlSessionTemplate) sqlSessionTemplate;
        customerSqlSessionTemplate.addMappedStatement(DataApiConstant.MAPPER_NAMESPACE, script, dsId);
        if ("select".equalsIgnoreCase(script.getOperationType())) {
            resultList = customerSqlSessionTemplate.selectList(DataApiConstant.MAPPER_NAMESPACE + ".query-" + script.getId() + "-" + version, params);
        } else if ("insert".equalsIgnoreCase(script.getOperationType())) {
            Integer insertResult = customerSqlSessionTemplate.insert(DataApiConstant.MAPPER_NAMESPACE + ".insert-" + script.getId() + "-" + version, params);
            Map<String, Integer> res = new HashMap<>();
            res.put("insert", insertResult);
            resultList.add(res);
        } else if ("update".equalsIgnoreCase(script.getOperationType())) {
            Integer updateResult = customerSqlSessionTemplate.update(DataApiConstant.MAPPER_NAMESPACE + ".update-" + script.getId() + "-" + version, params);
            Map<String, Integer> res = new HashMap<>();
            res.put("insert", updateResult);
            resultList.add(res);
        } else if ("delete".equalsIgnoreCase(script.getOperationType())) {
            Integer deleteResult = customerSqlSessionTemplate.delete(DataApiConstant.MAPPER_NAMESPACE + ".delete-" + script.getId() + "-" + version, params);
            Map<String, Integer> res = new HashMap<>();
            res.put("insert", deleteResult);
            resultList.add(res);
        } else {
            throw new BusinessException("Unknown Script Operation Type : " + script.getOperationType());
        }

        return resultList;
    }
}
