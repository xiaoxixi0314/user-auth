package com.github.xiaoxixi.auth.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSessionVO {

    private Long id;

    private String userCode;

    private String userName;

    private String refreshToken;

    private String accessToken;
}
