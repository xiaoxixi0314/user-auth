package com.github.xiaoxixi.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserAccessToken {

    private Long id;

    private Long userId;

    private String accessToken;

    private Date expireTime;

    private Long createBy;

    private Date gmtCreate;

    private Long modifyBy;

    private Date gmtModify;

}