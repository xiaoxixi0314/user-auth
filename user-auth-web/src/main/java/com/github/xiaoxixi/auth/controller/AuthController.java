package com.github.xiaoxixi.auth.controller;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import com.github.xiaoxixi.auth.exception.BizException;
import com.github.xiaoxixi.auth.result.Result;
import com.github.xiaoxixi.auth.service.LoginService;
import com.github.xiaoxixi.auth.utils.LoginUtils;
import com.github.xiaoxixi.auth.vo.UserSessionVO;
import com.xiaoxixi.service.register.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public Result<UserSessionVO> login(@RequestParam String userCode,
                                       @RequestParam String password,
                                       HttpServletResponse response){
        try {
            UserSessionVO session = loginService.login(userCode, password);
            Cookie cookie = new Cookie(Constants.TOKEN_COOKIE_NAME, session.getAccessToken());
            cookie.setMaxAge(Constants.ACCESS_TOKEN_EXPIRE_TIME);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.success(session);
        }catch (BizException be) {
            return Result.error(be.getMessage());
        } catch (Exception ex) {
            LOGGER.error("login system error:", ex);
            return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    @PostMapping("/refresh/token")
    public Result<UserSessionVO> refreshToken(@RequestParam Long userId,
                                              @RequestParam String refreshToken) {
        try {
            UserSessionVO session = loginService.refreshAccessToken(userId, refreshToken);
            return Result.success(session);
        } catch (BizException be) {
            return Result.error(be.getMessage());
        } catch (Exception ex) {
            LOGGER.error("refresh token error, user id:{}, refresh token:{}", userId, refreshToken, ex);
            return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request, HttpServletResponse response){
        try {
            // clear token in redis
            Cookie cookie = WebUtils.getCookie(request, Constants.TOKEN_COOKIE_NAME);
            if (Objects.isNull(cookie)){
                return Result.error(ErrorCodeEnum.ILLEGAL_USER);
            }
            String accessToken = cookie.getValue();
            cookie = new Cookie(Constants.TOKEN_COOKIE_NAME, "");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            // clear redis token
            String tokenRedisKey = LoginUtils.buildRedisTokenKey(accessToken);
            redisService.delete(tokenRedisKey);
            return Result.success(Boolean.TRUE);
        } catch (Exception e) {
            LOGGER.error("logout system error:", e);
            return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }
}
