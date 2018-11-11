package com.csw.common.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {

    private Integer port;

    private String serviceName;


}
