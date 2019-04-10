package com.tqfframe;

import com.tqfframe.config.TestConfig;
import com.tqfframe.domain.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@EnableEurekaClient  //Eureka客户端 :能够让注册中心能够发现，扫描到该服务
//@EnableWebMvc    //spring-boot+spring mvc
@SpringBootApplication
//@EnableDiscoveryClient  //@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心,如zookeeper
public class AdminApplication
{
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
        Customer customer = ctx.getBean(Customer.class);
        customer.setId(11);
        System.out.println(customer.getId());
    }
}
