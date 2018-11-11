package com.csw.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by csw on 2018/11/11.
 * Description:
 */
@Getter
@Setter
public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    private String desc;

    public BaseException(String desc) {
        this.desc = desc;
    }
}
