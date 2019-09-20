package com.three.develop.param;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by csw on 2019/09/19.
 * Description:
 */
@Builder
@Data
public class ColumnInfo {

    /**
     * 数据库字段名称
     **/
    @NotBlank
    private String columnName;

    /**
     * 数据库字段类型
     **/
    @NotBlank
    private String columnType;

    /**
     * 允许空值
     **/
    private String isNullable;

    /**
     * 数据库字段键类型
     **/
    private String columnKey;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 额外的参数
     **/
    private String extra;


    /**
     * 查询 1:模糊 2：精确
     **/
    private String columnQuery;

    /**
     * 是否在列表显示
     **/
    private String columnShow;
}
