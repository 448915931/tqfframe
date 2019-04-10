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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
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

    @Override
    public void configure(HttpSecurity http) throws Exception {

        System.out.println("Security加载。。。。。");

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
                //  /api/webapiurl/**表示包含/api/webapiurl/的所有路径都不需要认证，必须要2个*
                .antMatchers(
                        "/api/adminurl/produceapi/hello"
                        ,"/api/webapiurl/**").permitAll()
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
                .addFilter(new JWTLoginFilter(authenticationManager()))   //加载登陆拦截
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) //加载其他链接拦截，进行token认证！！！
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                .permitAll();
        // 禁用缓存
        http.headers().cacheControl();

    }

    // 该方法是登录的时候会进入
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("项目启动加载2。。。");
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
    }

}
