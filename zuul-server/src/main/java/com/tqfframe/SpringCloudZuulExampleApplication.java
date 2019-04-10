package com.tqfframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

/**
 * Hello world!
 *
 */
@EnableZuulProxy
@SpringBootApplication
@EnableEurekaClient
//@ComponentScan("com.tqfframe.config")//扫描到自己的配置文件，根据自己需要填写包名
public class SpringCloudZuulExampleApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SpringCloudZuulExampleApplication.class, args);
    }
}
