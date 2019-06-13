package com.github.xiaoxixi.auth.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {

    private Long id;

    private String userCode;

    private String userName;

    private String refreshToken;

    private String loginPwd;

    private Long createBy;

    private Date gmtCreate;

    private Long modifyBy;

    private Date gmtModify;

}