package com.csw.common.beans;

import com.csw.common.exception.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
@Getter
@Setter
public class Response<T, V> {

    private Integer code;

    private String desc;

    private ServiceInfo serviceInfo;

    private T result;

    private V schema;

    public static <T, V> Response success() {
        Response<T, V> response = new Response<T, V>();
        response.setCode(200);
        response.setDesc("success");
        return response;
    }

    public static <T, V> Response success(ServiceInfo serviceInfo) {
        Response<T, V> response = new Response<T, V>();
        response.setCode(200);
        response.setDesc("success");
        response.setServiceInfo(serviceInfo);
        return response;
    }


    public static <T, V> Response success(T t) {
        Response<T, V> response = success();
        response.setResult(t);
        return response;
    }

    public static <T, V> Response success(T t, V scheme) {
        Response<T, V> response = success(t);
        response.setSchema(scheme);
        response.setResult(t);
        return response;
    }

    public static <T, V> Response success(T t, ServiceInfo service) {
        Response<T, V> response = success(service);
        response.setResult(t);
        return response;
    }

    public static <T, V> Response fail(Throwable e, int statusCode) {
        Response<T, V> response = new Response<T, V>();
        response.setCode(statusCode);
        if (e instanceof BaseException) {
            BaseException ex = (BaseException) e;
            response.setDesc(ex.getDesc());
        } else {
            response.setDesc(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }

    public static <T, V> Response fail(Throwable e, int statusCode, ServiceInfo serviceInfo) {
        Response<T, V> response = new Response<T, V>();
        response.setCode(statusCode);
        response.setServiceInfo(serviceInfo);
        if (e instanceof BaseException) {
            BaseException ex = (BaseException) e;
            response.setDesc(ex.getDesc());
        } else {
            response.setDesc(ExceptionUtils.getStackTrace(e));
        }

        return response;
    }
}
