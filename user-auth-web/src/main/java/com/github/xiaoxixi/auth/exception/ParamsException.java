package com.github.xiaoxixi.auth.exception;


import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;

public class ParamsException extends RuntimeException {

    private String errorCode;

    private String message;

    public ParamsException(String message) {
        super(message);
        this.errorCode = "PARAMS_EXCEPTION";
        this.message = message;
    }

    public ParamsException(String message, Throwable ex) {
        super(message, ex);
        this.errorCode = "PARAMS_EXCEPTION";
        this.message = message;
    }

    public ParamsException(ErrorCodeEnum error) {
        super(error.getErrorMsg());
        this.message = error.getErrorMsg();
        this.errorCode = error.name();
    }

}
