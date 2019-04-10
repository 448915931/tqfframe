package com.tqfframe.config;

import com.tqfframe.domain.Customer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * Created by Tang-QiFeng on 2019/3/4
 */
@Configurable
public class TestConfig {

    @Bean
    public Customer configtest(){
        System.out.println("测试类配置文件");
        return new Customer();
    }
}
