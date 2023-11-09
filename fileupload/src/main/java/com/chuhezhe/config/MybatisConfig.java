package com.chuhezhe.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MybatisConfig
 * Package: com.chuhezhe.config
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/10/22 15:52
 * @Version 1.0
 */
@Configuration
@MapperScan("com.chuhezhe.mapper")
public class MybatisConfig {
}
