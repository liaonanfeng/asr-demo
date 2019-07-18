package com.ewt360.asr;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: wangchao
 * @create: 2019/7/11 14:03
 */
@EnableSwagger2Doc
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = "com.ewt360.asr.dal.mapper")
@SpringBootApplication(scanBasePackages = {"com.ewt360.asr"})
public class AsrDemoApplication {
    public static void main(String[] args){
        SpringApplication.run(AsrDemoApplication.class, args);
    }
}
