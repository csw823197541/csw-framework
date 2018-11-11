package com.csw.common.service.impl;

import com.csw.common.config.DataApiServiceConfig;
import com.csw.common.domain.Script;
import com.csw.common.exception.BaseException;
import com.csw.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDataApiService {

    @Autowired
    private DataApiServiceConfig dataApiServiceConfig;

    abstract Long apiNameToScriptIdMapping(String apiName) throws Exception;


    public Long convertApiNameToScriptId(String apiName) throws Exception {
        return apiNameToScriptIdMapping(apiName);
    }


    public void checkIfScriptEnabled(Script script) throws Exception {

        if (script.getEnabled().shortValue() != 1) {
            throw new BusinessException("please check if such script enabled for call");

        }
    }

    public boolean supportMultipleDataSource(Script script) {

        if (null != dataApiServiceConfig && null != dataApiServiceConfig.getDataApi()
                && dataApiServiceConfig.getDataApi().getIsMultiDataSource()) {
            return true;
        }
        return false;
    }


}
