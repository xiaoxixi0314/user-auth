package com.github.xiaoxixi.auth.dao;

import com.github.xiaoxixi.auth.domain.UserAccessToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserAccessTokenMapper {

    boolean deleteById(Long id);

    boolean insert(UserAccessToken record);

    boolean insertSelective(UserAccessToken record);

    UserAccessToken selectById(Long id);

    UserAccessToken selectByUserId(Long userId);

    boolean updateByUserIdSelective(UserAccessToken record);

    boolean updateByIdSelective(UserAccessToken record);

    boolean updateById(UserAccessToken record);
}