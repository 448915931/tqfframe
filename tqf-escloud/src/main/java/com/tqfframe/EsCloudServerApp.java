package com.tqfframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@EnableEurekaClient  //Eureka客户端 :能够让注册中心能够发现，扫描到该服务
@SpringBootApplication
public class EsCloudServerApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(EsCloudServerApp.class, args);
    }
}
