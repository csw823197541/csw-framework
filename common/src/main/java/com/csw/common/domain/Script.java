package com.csw.common.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by csw on 2018/11/10.
 * Description:
 */
@Getter
@Setter
@TableName("script")
public class Script {

    @TableId
    private String id;

    private String name;

    private String scriptEngineKey;

    private String datasourceKey;

    private String operationType;

    private String code;

    private Integer version;

    private Integer enabled;

}
