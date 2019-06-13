package com.github.xiaoxixi.auth.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    PARAM_CANT_EMPTY("参数不能为空"),
    NAME_PWD_CANT_EMPTY("用户名或密码不能为空"),
    NAME_PWD_ERROR("用户名或密码错误");

    private String errorMsg;

    ErrorCodeEnum(String errorMsg){
        this.errorMsg = errorMsg;
    }


}
