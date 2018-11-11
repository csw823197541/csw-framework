package com.csw.common.service.impl;


import com.csw.common.api.BeanRegister;
import com.csw.common.api.CustomSqlSessionTemplate;
import com.csw.common.beans.Response;
import com.csw.common.common.AppContextHolder;
import com.csw.common.domain.DataSource;
import com.csw.common.domain.Script;
import com.csw.common.domain.ScriptEngine;
import com.csw.common.engine.Engine;
import com.csw.common.exception.BaseException;
import com.csw.common.exception.BusinessException;
import com.csw.common.service.DataApiService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Component
//@ConditionalOnMissingBean(WharfDataService.class)
public class DataApiServiceImpl extends AbstractDataApiService implements DataApiService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private Map<String, Engine> engineMap;

//	@Autowired
//	private ScriptViewMapper scriptViewMapper;


    @Override
    public Response exec(Map<Object, Object> params, String apiName) throws Exception {
        List<Object> resultList = new ArrayList<>();
        List<HashMap> schemaList = new ArrayList<>();
        try {

//			Long id = convertApiNameToScriptId(apiName);
            String id = apiName;

            Script script = sqlSessionTemplate.selectOne("com.csw.common.dao.ScriptMapper.selectById", id);

            if (script == null) {
                throw new BusinessException("can not find script,id: " + id);
            }

            checkIfScriptEnabled(script);

            String scriptEngineKey = script.getScriptEngineKey();
            ScriptEngine scriptEngine = sqlSessionTemplate.selectOne("com.csw.common.dao.ScriptEngineMapper.selectByPrimaryKey", scriptEngineKey);

            Engine engine = engineMap.get(scriptEngine.getKey());

            if (supportMultipleDataSource(script)) {
                String name = script.getName();
                DataSource dataSource = sqlSessionTemplate.selectOne("com.csw.common.dao.DataSourceMapper.selectByPrimaryKey", name);

                // 注册数据源
                BeanRegister.registerDataSource(dataSource);

                // 注册mappedStatement
                CustomSqlSessionTemplate serviceSqlSession = (CustomSqlSessionTemplate) AppContextHolder.getApplicationContext().getBean("sqlSessionTemplate" + dataSource.getId());

                resultList = engine.execute(serviceSqlSession, params, script, dataSource);
            } else {
                resultList = engine.execute(sqlSessionTemplate, params, script, null);
            }

//            schemaList = scriptViewMapper.selectScriptViewByQueryId(apiName, LocaleUtils.getLocalLanguage());


        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionUtils.getStackTrace(e));
        }
        return Response.success(resultList, schemaList);
    }


    @Override
    Long apiNameToScriptIdMapping(String apiName) throws Exception {

//		AtomPrivilege param = new AtomPrivilege();
//		param.setName(apiName);
//		param.setRefType(PrivilegeType.SCRIPT.getDesc());
//
//		AtomPrivilege atomPrivilege = sqlSessionTemplate
//				.selectOne("com.xd.common.mapper.AtomPrivilegeMapper.selectByPrivilegeNameAndType", param);
//
//		if (null == atomPrivilege) {
//			throw new ConfigurationException(
//					"Privilege(Type " + param.getRefType() + ") " + apiName + " is not configured for user");
//		}
//
//		Long id = Long.parseLong(atomPrivilege.getRefScriptId().toString());

        return null;
    }

}
