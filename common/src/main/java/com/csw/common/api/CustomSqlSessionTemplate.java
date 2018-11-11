package com.csw.common.api;

import com.csw.common.config.DataApiServiceConfig;
import com.csw.common.constant.DataApiConstant;
import com.csw.common.domain.Script;
import com.csw.common.exception.BaseException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Component
public class CustomSqlSessionTemplate extends SqlSessionTemplate {

    public CustomSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Autowired
    private DataApiServiceConfig dataApiPropertyConfig;


    private String dirSplit = System.getProperty("os.name").toLowerCase().startsWith("win") ? "/" : System.getProperty("file.separator");

    /**
     * 生成mybatis mapper的 select/update/insert/delete id
     *
     * @return
     */
    private String getFunctionIdNoVersion(Script script) {
        return (script.getOperationType().equalsIgnoreCase("select") ? "query" : script.getOperationType().toLowerCase()) + "-" + script.getId();
    }

    private String getFunctionIdWithVersion(Script script) {
        return getFunctionIdNoVersion(script) + "-" + script.getVersion();
    }

    private String getFunctionIdWithPreVersion(Script script) {
        return getFunctionIdNoVersion(script) + "-" + (script.getVersion() - 1);
    }

    private String getStatementName(String functionId) {
        return DataApiConstant.MAPPER_NAMESPACE + "." + functionId;
    }

    private boolean isScriptDebug(Script script) {
        return 0 == script.getVersion();
    }

    /**
     * 删除掉session中多余的版本
     *
     * @param configuration
     * @param script
     */

    public synchronized void cleanOldVersion(Configuration configuration, Script script) {
        if (!this.isScriptDebug(script)) {

            try {
                String preFunctionId = this.getFunctionIdWithPreVersion(script);
                String preStatementName = this.getStatementName(preFunctionId);

                if (configuration.hasStatement(preStatementName)) {
                    removeConfig(configuration, preFunctionId);
                }

            } catch (Exception e) {
                // do nothing
            }

        }
    }

    /**
     * {@inheritDoc}
     *
     * @param
     */
    public synchronized <E> void addMappedStatement(String namespace, Script script, String dataSourceId) throws Exception {


        Configuration configuration = null;
        try {
            configuration = super.getSqlSessionFactory().getConfiguration();

            String functionId = this.getFunctionIdWithVersion(script);
            String statementName = this.getStatementName(functionId);

//			if(configuration.hasStatement("Query."+functionId) && !functionId.endsWith("debug"))
//				return;
//			if(configuration.hasStatement("Query."+functionId) && functionId.endsWith("debug")){
//					removeConfig(configuration, functionId);
//			}
            //版本号不为0则不覆盖
            if (configuration.hasStatement(statementName) && !this.isScriptDebug(script)) {
                return;
            }
            if (configuration.hasStatement(statementName) && this.isScriptDebug(script)) {
                removeConfig(configuration, functionId);
            }

            if (StringUtils.isEmpty(dataApiPropertyConfig.getMybatis().getMapping())) {
                throw new BaseException("config->nps.mybatis.mapping is not set, please make sure it is an valid path!");
            }
            File f = new File(dataApiPropertyConfig.getMybatis().getMapping() + dirSplit + DataApiConstant.MAPPER_NAMESPACE + "-" + dataSourceId + "-" + script.getId() + "-" + script.getVersion() + ".xml");

            //判断文件是否存在，不存在则尝试创建目录
            if (!f.exists()) {
                f.getParentFile().mkdirs();
            }

            synchronized (this) {
                if (!f.exists() || this.isScriptDebug(script)) {

                    StringBuffer fileBuffer = new StringBuffer("<?xml version='1.0' encoding='UTF-8' ?>"
                            + "<!DOCTYPE mapper PUBLIC '-//mybatis.org//DTD Mapper 3.0//EN' 'http://mybatis.org/dtd/mybatis-3-mapper.dtd'>"
                            + "<mapper namespace='" + DataApiConstant.MAPPER_NAMESPACE + "'>");
                    if ("insert".equalsIgnoreCase(script.getOperationType())) {
                        fileBuffer.append("	<insert id='" + functionId + "' parameterType='map' >" + script.getCode()
                                + "</insert></mapper>");
                    } else if ("update".equalsIgnoreCase(script.getOperationType())) {
                        fileBuffer.append("	<update id='" + functionId + "' parameterType='map' >" + script.getCode()
                                + "</update></mapper>");
                    } else if ("delete".equalsIgnoreCase(script.getOperationType())) {
                        fileBuffer.append("	<delete id='" + functionId + "' parameterType='map' >" + script.getCode()
                                + "</delete></mapper>");
                    } else {
                        fileBuffer.append("	<select id='" + functionId + "' parameterType='map' resultType='map'>"
                                + script.getCode() + "</select></mapper>");
                    }
                    String file = fileBuffer.toString();
                    f.createNewFile();
                    OutputStreamWriter dos = new OutputStreamWriter(
                            new FileOutputStream(dataApiPropertyConfig.getMybatis().getMapping() + dirSplit + f.getName()), "UTF-8");
                    dos.write(file);
                    dos.close();

                }
                XMLMapperBuilder xmb = new XMLMapperBuilder(new FileInputStream(f),
                        configuration, functionId, configuration.getSqlFragments(), DataApiConstant.MAPPER_NAMESPACE);
                xmb.parse();

            }

        } catch (Exception e) {
            throw e;
        } finally {

            cleanOldVersion(configuration, script);

        }
    }

    private synchronized void removeConfig(Configuration configuration, String queryId) throws Exception {
        Class<?> classConfig = configuration.getClass();
        clearMap(classConfig, configuration, "mappedStatements", queryId);
        clearMap(classConfig, configuration, "caches", null);
        clearMap(classConfig, configuration, "resultMaps", null);
        clearMap(classConfig, configuration, "parameterMaps", null);
        clearMap(classConfig, configuration, "keyGenerators", null);
        clearMap(classConfig, configuration, "sqlFragments", null);
        clearSet(classConfig, configuration, "loadedResources", queryId);

    }

    private synchronized void clearMap(Class<?> classConfig, Configuration configuration, String fieldName, String functionId) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map mapConfig = (Map) field.get(configuration);
        if (mapConfig.containsKey(functionId)) {
            mapConfig.remove(functionId);
        }
        if (mapConfig.containsKey(getStatementName(functionId))) {
            mapConfig.remove(getStatementName(functionId));
        }
    }

    private synchronized void clearSet(Class<?> classConfig, Configuration configuration, String fieldName, String functionId) throws Exception {
        Field field = classConfig.getDeclaredField(fieldName);
        field.setAccessible(true);
        Set setConfig = (Set) field.get(configuration);
        if (setConfig.contains(functionId)) {
            setConfig.remove(functionId);
        }
        if (setConfig.contains(getStatementName(functionId))) {
            setConfig.remove(getStatementName(functionId));
        }
    }
}
