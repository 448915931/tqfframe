package com.tqfframe.filter;

import com.tqfframe.constant.ConstantKey;
import com.tqfframe.exception.TokenException;
import com.tqfframe.handler.GrantedAuthorityImpl;
import com.tqfframe.redis.RedisUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义JWT认证过滤器
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author zhaoxinguo on 2017/9/13.
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private RedisUtil redisUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil=redisUtil;
    }


    //用户请求时，接收header
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("token");
        System.out.println("---------------访问1");
        System.out.println(header);
        //判断可以是否存在
        Boolean ishaskey=redisUtil.hasKey("token:"+header);
        System.out.println(ishaskey);
        if (ishaskey==false || header == null || !header.startsWith("Bearer-")) {
            chain.doFilter(request, response);
            return;
        }
        System.out.println("----------------访问2");
        //如果带了header，进入下面方法
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        //参数传递到authentication对象，来建立安全上下文（security context），也就是权限放入Authentication中，其他类使用
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();去获取权限信息等
        SecurityContextHolder.getContext().setAuthentication(authentication);       //set进Authentication
        chain.doFilter(request, response);  //放行通过
    }

    // 解析JWT字符串
    //username和password被获得后封装到一个UsernamePasswordAuthenticationToken（Authentication接口的实例）的实例中
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new TokenException("Token为空");
        }
        // 解析JWT字符串
        String user = null;
        try {
            user = Jwts.parser()
                    .setSigningKey(ConstantKey.SIGNING_KEY)     //通过签名key去判断是自己创建的token
                    .parseClaimsJws(token.replace("Bearer-", ""))
                    .getBody()
                    .getSubject();
            System.out.println(user);       //解析出用户信息
            long end = System.currentTimeMillis();
            logger.info("执行时间: {}", (end - start) + " 毫秒");
            if (user != null) {
                String[] split = user.split("-")[1].split(",");
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (int i=0; i < split.length; i++) {
                    System.out.println(split[i]);
                    authorities.add(new GrantedAuthorityImpl(split[i]));
                }
                System.out.println(authorities);    //权限信息
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }

        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new TokenException("Token已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new TokenException("Token格式错误");
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new TokenException("Token没有被正确构造");
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new TokenException("签名失败");
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new TokenException("非法参数异常");
        }

        return null;
    }

}
