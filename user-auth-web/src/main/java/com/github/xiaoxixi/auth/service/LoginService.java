package com.github.xiaoxixi.auth.service;


import com.github.xiaoxixi.auth.exception.BizException;
import com.github.xiaoxixi.auth.vo.UserAccessTokenVO;
import com.github.xiaoxixi.auth.vo.UserSessionVO;

public interface LoginService {

    UserSessionVO login(String userCode, String password) throws BizException;

    UserAccessTokenVO verifyAccessToken(String accessToken) throws BizException;

    boolean checkLoginPwd(String userCode, String password) throws BizException;

    UserSessionVO refreshToken(Long userId, String refreshToken) throws BizException;
}
