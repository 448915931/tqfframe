package com.tqfframe.jwt;

import com.tqfframe.filter.JWTAuthenticationFilter;
import com.tqfframe.filter.JWTLoginFilter;
import com.tqfframe.handler.Http401AuthenticationEntryPoint;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

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
            http.csrf().disable()   // 由于使用的是JWT，我们这里不需要csrf,csrf是Spring2.0以后默认开起的安全验证，如果使用Security，会冲突，需要关闭掉csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()          // 基于token，所以不需要session
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
                    //允许通过的接口
                .antMatchers("/login",      //登录接口不用过滤
                        "/**/authcenterurl/login",     //登录接口不用过滤
                        "/**/users/registe"             //注册接口不用过滤
//                        ,"/produceapi/hello"      //如果哪个接口不想被认证可以这样
//                        ,"/api/webapiurl/**"     //允许整个目录不需要认证
                ).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()   //允许swagger相关的路径通关
                .anyRequest().authenticated().and()     //除上面外的所有请求全部需要鉴权认证
                .exceptionHandling()                    //异常返回
                .authenticationEntryPoint(
                        new Http401AuthenticationEntryPoint("Basic realm=\"MyApp\"")
                ).and()
//                .addFilter(new JWTLoginFilter(authenticationManager))   //加载登陆拦截，因为使用了自定义控制器，所以这里不需要了
                .addFilter(new JWTAuthenticationFilter(authenticationManager)); //加载拦截，进行token认证！！！
//                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
//                .permitAll();
        // 禁用缓存
        http.headers().cacheControl();
    }
}