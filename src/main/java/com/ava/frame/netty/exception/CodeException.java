package com.ava.frame.netty.exception;

/**
 * Created by redred on 2016/8/10.
 */


import com.ava.frame.netty.constant.CodeDefinition;

/**
 * lanjian on 2014/7/24.
 */
public class CodeException extends RuntimeException {


    private Integer code= CodeDefinition.FAIL_CODE;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CodeException() {
    }

    public CodeException(String message) {
        super(message);
    }


    public CodeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public CodeException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CodeException(Throwable cause) {
        super(cause);
    }
}
