package com.tqfframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class AuthCenterApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthCenterApp.class, args);
    }
}
