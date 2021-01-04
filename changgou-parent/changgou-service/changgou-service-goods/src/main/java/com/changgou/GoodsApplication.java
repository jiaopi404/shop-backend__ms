package com.changgou;

import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient // 开启 Eureka 客户端
// 开启通用 Mapper 的包扫描
// 注解包名: tk.mybatis.spring.annotation.MapperScan; tk 开头
@MapperScan(basePackages = {"com.changgou.dao"}, annotationClass = Repository.class)
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    /**
     * IdWorker 生成唯一 id
     */
    @Bean
    public IdWorker idWorker () {
        return new IdWorker(0, 0);
    }
}
