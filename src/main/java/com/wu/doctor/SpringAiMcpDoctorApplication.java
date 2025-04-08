package com.wu.doctor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 主启动类
 *
 */
@SpringBootApplication
@MapperScan("com.wu.doctor.mapper")
public class SpringAiMcpDoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiMcpDoctorApplication.class, args);
    }

}
