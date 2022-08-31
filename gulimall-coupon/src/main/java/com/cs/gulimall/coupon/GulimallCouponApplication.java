package com.cs.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 远程调用别的服务
 * 1.引入openfeign
 * 2.编写接口,并且在接口中书写方法,来调用哪个服务的哪个方法
 * 3.开启远程调用的功能
 */
@SpringBootApplication
//开启服务发现,nacos服务
@EnableDiscoveryClient
//开启feign
@EnableFeignClients
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
