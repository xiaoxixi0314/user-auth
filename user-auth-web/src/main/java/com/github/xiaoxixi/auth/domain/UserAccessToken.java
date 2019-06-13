package com.github.xiaoxixi.auth.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserAccessToken {

    private Long id;

    private Long userId;

    private String accessToken;

    private Date expireAt;

    private Long createBy;

    private Date gmtCreate;

    private Long modifyBy;

    private Date gmtModify;

}