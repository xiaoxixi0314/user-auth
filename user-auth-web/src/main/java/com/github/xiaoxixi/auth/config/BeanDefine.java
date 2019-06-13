package com.github.xiaoxixi.auth.config;

import com.xiaoxixi.service.register.ServiceRegisterConfig;
import com.xiaoxixi.service.register.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefine {

    @Autowired
    private ServiceRegisterConfig serviceRegisterConfig;

    @Bean
    public RedisService redisService() {
        return serviceRegisterConfig.getRedisService();
    }

}
