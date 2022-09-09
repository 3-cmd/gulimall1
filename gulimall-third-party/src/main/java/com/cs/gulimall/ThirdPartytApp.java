package com.cs.gulimall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ThirdPartytApp {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartytApp.class,args);
    }
}
