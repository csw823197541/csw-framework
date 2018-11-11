package com.csw.common.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
@Getter
@Setter
@TableName("script_engine")
public class ScriptEngine {

    @TableId
    private Integer id;

    private String key;

    private String type;

    private Integer version;
}
