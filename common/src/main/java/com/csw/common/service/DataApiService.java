package com.csw.common.service;

import com.csw.common.beans.Response;

import java.util.Map;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
public interface DataApiService {

    Response exec(Map<Object, Object> params, String apiName) throws Exception;
}
