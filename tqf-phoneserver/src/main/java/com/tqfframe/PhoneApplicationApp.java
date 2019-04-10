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
public class PhoneApplicationApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(PhoneApplicationApp.class, args);
    }
}
