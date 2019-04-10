package com.tqfframe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Tang-QiFeng on 2019/4/8
 */


@Configuration
@EnableWebMvc
public class ApplicationExceptionAdapter extends WebMvcConfigurerAdapter {

    /**
     *   http://localhost:9090/swagger-ui.html
     *   自定义资源映射addResourceHandlers
     *   addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
     *   这里，swagger-ui.html是对外暴露地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("自定义资源映射addResourceHandlers。。。。。");
        //registry.addResourceHandler("docs.html")
        //		.addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}