package com.xe.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//(exclude={DataSourceAutoConfiguration.class})和mapperScan好像不能同用
//只能将它写到yml配置文件中
@SpringBootApplication
@MapperScan(basePackages = "com.xe.core.mapper")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
