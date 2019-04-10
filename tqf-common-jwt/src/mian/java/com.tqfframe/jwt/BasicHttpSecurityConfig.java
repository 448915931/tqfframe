package com.tqfframe.jwt;

import com.tqfframe.filter.JWTAuthenticationFilter;
import com.tqfframe.filter.JWTLoginFilter;
import com.tqfframe.handler.Http401AuthenticationEntryPoint;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by Tang-QiFeng on 2019/4/10
 */
public class BasicHttpSecurityConfig {

    /**
     * 允许swagger相关的url通关
     */
    private static final String[] AUTH_WHITELIST = {
            "/**/v2/api-docs",      //所有/v2/api-docs的路由
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html"
    };


    public static void basicHttpSecurity(HttpSecurity http,AuthenticationManager authenticationManager) throws Exception {

        //配置需要进过认证的url和不需要认证的url
        LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer = http
                // 由于使用的是JWT，我们这里不需要csrf,csrf是Spring2.0以后默认开起的安全验证，如果使用Security，会冲突，需要关闭掉csrf
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
                // 允许通过的接口
//                /api/webapiurl/**表示包含/api/webapiurl/的所有路径都不需要认证，必须要2个*
//                .antMatchers(
//                        "/api/adminurl/produceapi/hello"
//                        ,"/api/webapiurl/**").permitAll()
                .antMatchers("/login",
                        "/**/users/registe",
                        "/produceapi/hello"
                ).permitAll()
                //允许swagger相关的路径通关
                .antMatchers(AUTH_WHITELIST).permitAll()
                /**
                 *  除 上面外的所有请求全部需要鉴权认证
                 */
                .anyRequest().authenticated().and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        new Http401AuthenticationEntryPoint("Basic realm=\"MyApp\"")
                ).and()
                .addFilter(new JWTLoginFilter(authenticationManager))   //加载登陆拦截
                .addFilter(new JWTAuthenticationFilter(authenticationManager)) //加载其他链接拦截，进行token认证！！！
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                .permitAll();
        // 禁用缓存
        http.headers().cacheControl();
    }
}
