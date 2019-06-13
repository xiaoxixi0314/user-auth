package com.github.xiaoxixi.auth.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TokenParam {

    private Long userId;

    private Long currentTime;

    private String userCode;

    private String userName;

    private String loginPwd;

    private String salt;

}
