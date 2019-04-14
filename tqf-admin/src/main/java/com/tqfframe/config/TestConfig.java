package com.tqfframe.config;

import com.tqfframe.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * Created by Tang-QiFeng on 2019/3/4
 */
@Configurable
public class TestConfig {

    @Bean
    public Customer configtest(){
        System.out.println("测试注入配置文件");
        return new Customer(1,"2","3");
    }
    //对于标注了@bean的方法，可以在任何注解类中注入调用！
    @Autowired
    public Customer customer;

}
