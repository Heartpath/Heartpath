package com.zootopia.userservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "com.zootopia.userservice.mapper")
public class MyBatisConfig {
}
