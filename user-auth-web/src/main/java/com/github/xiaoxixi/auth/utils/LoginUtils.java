package com.github.xiaoxixi.auth.utils;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.domain.UserAccessToken;
import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import com.github.xiaoxixi.auth.exception.ParamsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUtils.class);

    private LoginUtils(){}

    public static String buildRedisTokenKey(UserAccessToken token) {
        if (Objects.isNull(token)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        Long userId = token.getUserId();
        String accessToken = token.getAccessToken();
        return buildRedisTokenKey(userId, accessToken);
    }

    public static String buildRedisTokenKey(Long userId, String accessToken) {
        if (Objects.isNull(userId) || Objects.isNull(accessToken)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        return new StringBuilder().append(Constants.REDIS_TOKEN_PREFIX)
                .append(Constants.REDIS_KEY_SPLIT)
                .append(userId)
                .append(accessToken)
                .toString();
    }
}
