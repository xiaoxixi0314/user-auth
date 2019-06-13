package com.github.xiaoxixi.auth.dao;

import com.github.xiaoxixi.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    boolean deleteById(Long id);

    boolean insert(User record);

    boolean insertSelective(User record);

    User selectById(Long id);

    User selectByUserCode(String userCode);

    boolean updateByIdSelective(User record);

    boolean updateById(User record);
}