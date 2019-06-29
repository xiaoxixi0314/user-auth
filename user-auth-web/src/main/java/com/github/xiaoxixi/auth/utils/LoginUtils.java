package com.github.xiaoxixi.auth.utils;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.domain.UserAccessToken;
import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import com.github.xiaoxixi.auth.exception.ParamsException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.Objects;

public class LoginUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUtils.class);

    private LoginUtils(){}

    public static String buildRedisTokenKey(UserAccessToken token) {
        if (Objects.isNull(token)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        String accessToken = token.getAccessToken();
        return buildRedisTokenKey(accessToken);
    }

    public static String buildRedisTokenKey(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        return new StringBuilder().append(Constants.REDIS_TOKEN_PREFIX)
                .append(Constants.REDIS_KEY_SPLIT)
                .append(accessToken)
                .toString();
    }

    public static Cookie buildAccessTokenCookie(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            throw new ParamsException("cookie value cant be null");
        }
        Cookie cookie = new Cookie(Constants.TOKEN_COOKIE_NAME, accessToken);
        cookie.setMaxAge(Constants.ACCESS_TOKEN_EXPIRE_TIME);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
