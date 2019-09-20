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
public class GenConfig {

    /**
     * 包路径
     **/
    @NotBlank
    private String pack;

    /**
     * 模块名
     **/
    private String moduleName;

    /**
     * 后端代码文件路径
     **/
    @NotBlank
    private String path;

    /**
     * 前端代码文件路径
     **/
    private String apiPath;

    /**
     * 作者
     **/
    private String author;

    /**
     * 表前缀
     **/
    private String prefix;

    /**
     * 是否覆盖
     **/
    private Boolean cover = Boolean.TRUE;
}
