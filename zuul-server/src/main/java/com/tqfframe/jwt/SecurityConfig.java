package com.tqfframe.jwt;

import com.tqfframe.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 *  一些理解：如果只配zuul的jwt认证，那么进入zuul网关的接口会进行认证。
 *  如果不走网关，直接写接口路径的话就会绕过zuul配置的jwt认证，所以必须每个需要认证的项目都要开启@EnableWebSecurity
 *  WebSecurityConfigurerAdapter是对应用中安全框架的个性化定制
 * Created by Tang-QiFeng on 2019/4/9
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //把SecurityContextLogoutHandler对象注入到spring容器中，供其他标注了注解的类注入使用！！！
    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler(){
        return new SecurityContextLogoutHandler();
    }

    @Autowired
    private SecurityContextLogoutHandler securityContextLogoutHandler;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * HTTP请求处理
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("Security加载。。。。。");
        BasicHttpSecurityConfig.basicHttpSecurity(http,authenticationManager(),securityContextLogoutHandler,redisUtil);
    }
    /**
     * 授权验证服务
     * 该方法是登录的时候会进入
     */
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("项目启动加载2。。。");
//        // 使用自定义身份验证组件
//        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
//    }

}
