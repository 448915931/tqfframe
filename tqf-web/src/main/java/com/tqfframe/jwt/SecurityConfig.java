package com.tqfframe.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    /**
     * 允许swagger相关的url通关
     */
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("Security加载。。。。。");
        //配置需要进过认证的url和不需要认证的url
         http
                // 由于使用的是JWT，我们这里不需要csrf,csrf是Spring2.0以后默认开起的安全验证，如果使用Security，会冲突，需要关闭掉csrf，不关掉会结果所有路由弹出认证！！！
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                 .antMatchers("/users/registe","/testapi/testhello").permitAll()
                 //允许swagger相关的路径通关
                 .antMatchers(AUTH_WHITELIST).permitAll()
                 //上面的路径直接通过，其他路径都经过拦截进行认证处理
                 .anyRequest().authenticated().and()
                 .exceptionHandling();
        // 禁用缓存
        http.headers().cacheControl();

    }

}
