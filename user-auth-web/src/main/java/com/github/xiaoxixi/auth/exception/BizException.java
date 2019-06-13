package com.github.xiaoxixi.auth.exception;


import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import lombok.Getter;

public class BizException extends Exception {

    private String errorCode;

    @Getter
    private String message;

    public BizException(String message) {
        super(message);
        this.message = message;
        this.errorCode = "BIZ_EXCEPTION";
    }

    public BizException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.errorCode = "BIZ_EXCEPTION";
    }

    public BizException(ErrorCodeEnum error) {
        super(error.getErrorMsg());
        this.message = error.getErrorMsg();
        this.errorCode = error.name();
    }

}
