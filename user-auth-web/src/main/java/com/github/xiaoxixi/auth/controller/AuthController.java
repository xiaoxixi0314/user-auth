package com.github.xiaoxixi.auth.controller;

import com.github.xiaoxixi.auth.contants.Constants;
import com.github.xiaoxixi.auth.enums.ErrorCodeEnum;
import com.github.xiaoxixi.auth.exception.BizException;
import com.github.xiaoxixi.auth.result.Result;
import com.github.xiaoxixi.auth.service.LoginService;
import com.github.xiaoxixi.auth.vo.UserSessionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoginService loginService;

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
            LOGGER.error("login sysrem error:", ex);
            return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    @PostMapping("/refresh/token")
    public Result<UserSessionVO> refreshToken(@RequestParam Long userId,
                                              @RequestParam String refreshToken) {
        try {
            UserSessionVO seesion = loginService.refreshAccessToken(userId, refreshToken);
            return Result.success(seesion);
        } catch (BizException be) {
            return Result.error(be.getMessage());
        } catch (Exception ex) {
            LOGGER.error("refresh token error, user id:{}, refresh token:{}", userId, refreshToken, ex);
            return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }
}
