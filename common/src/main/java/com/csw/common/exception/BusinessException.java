package com.csw.common.exception;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String desc) {
        super(desc);
    }
}
