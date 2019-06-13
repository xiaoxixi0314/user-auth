package com.github.xiaoxixi.auth.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccessTokenVO {

    private Long userId;

    private String accessToken;
}
