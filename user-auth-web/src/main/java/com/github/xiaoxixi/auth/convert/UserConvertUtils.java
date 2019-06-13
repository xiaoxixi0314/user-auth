package com.github.xiaoxixi.auth.convert;


import com.github.xiaoxixi.auth.domain.User;
import com.github.xiaoxixi.auth.vo.UserSessionVO;

public class UserConvertUtils {

    public static UserSessionVO covertToUserSessionVO(User user) {
        UserSessionVO session = new UserSessionVO();
        session.setId(user.getId());
        session.setUserCode(user.getUserCode());
        session.setUserName(user.getUserName());
        session.setRefreshToken(user.getRefreshToken());
        return session;
    }
}
