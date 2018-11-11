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
@TableName("data_source")
public class DataSource {

    @TableId
    private Integer id;

    private String key;

    private String driveClassName;

    private String url;

    private String username;

    private String password;

    private String version;

}
