package com.wu.doctor.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson配置类
 * 提供全局Jackson ObjectMapper配置
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置全局ObjectMapper
     * 启用Java 8日期时间模块
     * 禁用日期时间类型作为时间戳输出
     *
     * @return 配置好的ObjectMapper实例
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 注册Java 8日期时间模块
        objectMapper.registerModule(new JavaTimeModule());
        
        // 禁用将日期写为时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        return objectMapper;
    }
}
