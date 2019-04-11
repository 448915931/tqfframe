package com.tqfframe.jwt;

import com.tqfframe.filter.JWTAuthenticationFilter;
import com.tqfframe.filter.JWTLoginFilter;
import com.tqfframe.handler.CustomAuthenticationProvider;
import com.tqfframe.handler.Http401AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

/**
 *  一些理解：如果只配zuul的jwt认证，那么进入zuul网关的接口会进行认证。
 *  如果不走网关，直接写接口路径的话就会绕过zuul配置的jwt认证，所以必须每个需要认证的项目都要开启@EnableWebSecurity
 *  WebSecurityConfigurerAdapter是对应用中安全框架的个性化定制
 * Created by Tang-QiFeng on 2019/4/9
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    //配置BCryptPasswordEncoderbean，这样其他类中就可以注入BCryptPasswordEncoder了
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * HTTP请求处理
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("Security加载。。。。。");
        BasicHttpSecurityConfig.basicHttpSecurity(http,authenticationManager());
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
