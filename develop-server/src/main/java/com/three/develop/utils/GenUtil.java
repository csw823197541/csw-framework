package com.three.develop.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.*;
import com.three.common.utils.StringUtil;
import com.three.develop.param.ColumnInfo;
import com.three.develop.param.GenConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csw on 2019/09/19.
 * Description:
 */
@Slf4j
public class GenUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIG_DECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    private static final String EXTRA = "auto_increment";

    /**
     * 生成代码
     *
     * @param columnInfoList 表元数据
     * @param genConfig      生成代码的参数配置，如包路径，作者
     */
    public static void generateCode(List<ColumnInfo> columnInfoList, GenConfig genConfig, String tableName) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("package", genConfig.getPack());
        map.put("moduleName", genConfig.getModuleName());
        map.put("author", genConfig.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        String className = StringUtil.toCapitalizeCamelCase(tableName);
        String changeClassName = StringUtil.toCamelCase(tableName);

        // 判断是否去除表前缀
        if (StringUtil.isNotBlank(genConfig.getPrefix())) {
            className = StringUtil.toCapitalizeCamelCase(StringUtil.removePrefix(tableName, genConfig.getPrefix()));
            changeClassName = StringUtil.toCamelCase(StringUtil.removePrefix(tableName, genConfig.getPrefix()));
        }
        map.put("className", className);
        map.put("upperCaseClassName", className.toUpperCase());
        map.put("changeClassName", changeClassName);
        map.put("hasTimestamp", false);
        map.put("hasBigDecimal", false);
        map.put("hasQuery", false);
        map.put("auto", false);

        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : columnInfoList) {
            Map<String, Object> listMap = new HashMap<>();
            listMap.put("columnComment", column.getColumnComment());
            listMap.put("columnKey", column.getColumnKey());
            String colType = column.getColumnType();
            String changeColumnName = StringUtil.toCamelCase(column.getColumnName());
            String capitalColumnName = StringUtil.toCapitalizeCamelCase(column.getColumnName());
            if (PK.equals(column.getColumnKey())) {
                map.put("pkColumnType", colType);
                map.put("pkChangeColName", changeColumnName);
                map.put("pkCapitalColName", capitalColumnName);
            }
            if (TIMESTAMP.equals(colType)) {
                map.put("hasTimestamp", true);
            }
            if (BIG_DECIMAL.equals(colType)) {
                map.put("hasBigDecimal", true);
            }
            if (EXTRA.equals(column.getExtra())) {
                map.put("auto", true);
            }
            listMap.put("columnType", colType);
            listMap.put("columnName", column.getColumnName());
            listMap.put("isNullable", column.getIsNullable());
            listMap.put("columnShow", column.getColumnShow());
            listMap.put("changeColumnName", changeColumnName);
            listMap.put("capitalColumnName", capitalColumnName);

            // 判断是否有查询，如有则把查询的字段set进columnQuery
            if (!StringUtil.isBlank(column.getColumnQuery())) {
                listMap.put("columnQuery", column.getColumnQuery());
                map.put("hasQuery", true);
                queryColumns.add(listMap);
            }
            columns.add(listMap);
        }
        map.put("columns", columns);
        map.put("queryColumns", queryColumns);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        // 生成后端代码
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates) {
            Template template = engine.getTemplate("generator/admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, genConfig, className);

            File file = null;
            if (filePath != null) {
                file = new File(filePath);
            }

            // 如果非覆盖生成
            if (!genConfig.getCover() && FileUtil.exist(file)) {
                continue;
            }
            // 生成代码
            genFile(file, template, map);
        }
    }

    /**
     * 获取后端代码模板名称
     *
     * @return
     */
    private static List<String> getAdminTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity");
//        templateNames.add("Repository");
//        templateNames.add("Service");
//        templateNames.add("QueryCriteria");
//        templateNames.add("Controller");
        return templateNames;
    }

    /**
     * 获取前端代码模板名称
     *
     * @return
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("api");
        templateNames.add("index");
        templateNames.add("eForm");
        return templateNames;
    }

    /**
     * 定义后端文件路径以及名称
     */
    private static String getAdminFilePath(String templateName, GenConfig genConfig, String className) {
        String projectPath = System.getProperty("user.dir") + File.separator + genConfig.getModuleName();
        String packagePath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator;
        }

        if ("Entity".equals(templateName)) {
            return packagePath + "domain" + File.separator + className + ".java";
        }

        if ("Controller".equals(templateName)) {
            return packagePath + "rest" + File.separator + className + "Controller.java";
        }

        if ("Service".equals(templateName)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if ("ServiceImpl".equals(templateName)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if ("Dto".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "DTO.java";
        }

        if ("QueryCriteria".equals(templateName)) {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "QueryCriteria.java";
        }

        if ("Mapper".equals(templateName)) {
            return packagePath + "service" + File.separator + "mapper" + File.separator + className + "Mapper.java";
        }

        if ("Repository".equals(templateName)) {
            return packagePath + "repository" + File.separator + className + "Repository.java";
        }

        return null;
    }

    /**
     * 定义前端文件路径以及名称
     */
    private static String getFrontFilePath(String templateName, GenConfig genConfig, String apiName) {
        String path = genConfig.getPath();

        if ("api".equals(templateName)) {
            return genConfig.getApiPath() + File.separator + apiName + ".js";
        }

        if ("index".equals(templateName)) {
            return path + File.separator + "index.vue";
        }

        if ("eForm".equals(templateName)) {
            return path + File.separator + File.separator + "form.vue";
        }
        return null;
    }

    private static void genFile(File file, Template template, Map<String, Object> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(file);
            writer = new FileWriter(file);
            template.render(map, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
