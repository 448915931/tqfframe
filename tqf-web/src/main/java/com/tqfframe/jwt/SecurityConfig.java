package com.tqfframe.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.List;

/**
 *  一些理解：如果只配zuul的jwt认证，那么进入zuul网关的接口会进行认证。
 *  如果不走网关，直接写接口路径的话就会绕过zuul配置的jwt认证，所以必须每个需要认证的项目都要开启@EnableWebSecurity
 *
 * Created by Tang-QiFeng on 2019/4/9
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //配置BCryptPasswordEncoderbean，这样其他类中就可以注入BCryptPasswordEncoder了
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    //把SecurityContextLogoutHandler对象注入到spring容器中，供其他标注了注解的类注入使用！！！
    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler(){
        return new SecurityContextLogoutHandler();
    }

    @Autowired
    private SecurityContextLogoutHandler securityContextLogoutHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("Security加载。。。。。");
        BasicHttpSecurityConfig.basicHttpSecurity(http,authenticationManager(),securityContextLogoutHandler);
    }

}
