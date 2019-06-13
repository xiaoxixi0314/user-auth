package com.github.xiaoxixi.auth.service;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.convert.UserConvertUtils;
import com.github.xiaoxixi.auth.dao.UserAccessTokenMapper;
import com.github.xiaoxixi.auth.dao.UserMapper;
import com.github.xiaoxixi.auth.domain.User;
import com.github.xiaoxixi.auth.domain.UserAccessToken;
import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import com.github.xiaoxixi.auth.exception.BizException;
import com.github.xiaoxixi.auth.exception.ParamsException;
import com.github.xiaoxixi.auth.utils.Md5Utils;
import com.github.xiaoxixi.auth.vo.TokenParam;
import com.github.xiaoxixi.auth.vo.UserAccessTokenVO;
import com.github.xiaoxixi.auth.vo.UserSessionVO;
import com.xiaoxixi.service.register.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccessTokenMapper userAccessTokenMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public UserSessionVO login(String userCode, String password) throws BizException{
        if (StringUtils.isAnyBlank(userCode, password)) {
            throw new BizException(ErrorCodeEnum.NAME_PWD_CANT_EMPTY);
        }
        User user = userMapper.selectByUserCode(userCode);
        if (Objects.isNull(user)) {
            throw new BizException(ErrorCodeEnum.NAME_PWD_ERROR);
        }
        String encryptPwd = Md5Utils.encrypt(password);
        if (!Objects.equals(encryptPwd, user.getLoginPwd())) {
            throw new BizException(ErrorCodeEnum.NAME_PWD_ERROR);
        }
        UserSessionVO session = UserConvertUtils.covertToUserSessionVO(user);
        String accessToken = refreshAccessToken(user, false);
        session.setAccessToken(accessToken);
        return session;
    }

    private String refreshAccessToken(User user, boolean forceRefresh) throws BizException{
        if (Objects.isNull(user)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        UserAccessToken token = userAccessTokenMapper.selectByUserId(user.getId());
        Date now = new Date();
        if (Objects.isNull(token)) {
            String accessToken = generateAccessToken(user);
            token = new UserAccessToken();
            token.setUserId(user.getId());
            token.setAccessToken(accessToken);
            token.setExpireIn(Constants.ACCESS_TOKEN_EXPIRE_TIME);
            token.setCreateBy(user.getId());
            token.setModifyBy(user.getId());
            token.setGmtCreate(now);
            token.setGmtModify(now);
            userAccessTokenMapper.insertSelective(token);
            return accessToken;
        }

        if (forceRefresh) {
            String accessToken = generateAccessToken(user);
            token = new UserAccessToken();
            token.setUserId(user.getId());
            token.setGmtModify(now);
            token.setModifyBy(user.getId());
            token.setAccessToken(accessToken);
            token.setExpireIn(Constants.ACCESS_TOKEN_EXPIRE_TIME);
            userAccessTokenMapper.updateByUserIdSelective(token);
            return accessToken;
        }

        return token.getAccessToken();

    }

    private String generateAccessToken(User user) throws BizException{
        if (Objects.isNull(user)) {
            throw new ParamsException(ErrorCodeEnum.PARAM_CANT_EMPTY);
        }
        String tokenParam = TokenParam.builder().userId(user.getId())
                .userCode(user.getUserCode())
                .userName(user.getUserName())
                .loginPwd(user.getLoginPwd())
                .currentTime(System.currentTimeMillis())
                .salt(Constants.ACCESS_TOKEN_SALT)
                .build().toString();
        return Md5Utils.encrypt(tokenParam);
    }

    @Override
    public UserAccessTokenVO verifyAccessToken(String accessToken)throws BizException {
        return null;
    }

    @Override
    public boolean checkLoginPwd(String userCode, String password)throws BizException{
        return false;
    }

    @Override
    public UserSessionVO refreshToken(Long userId, String refreshToken)throws BizException{
        return null;
    }
}