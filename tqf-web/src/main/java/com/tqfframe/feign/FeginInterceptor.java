package com.tqfframe.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *  fegin是不经过zuul网关的！！！其实可以不用配置
 *  把token加入到Fegin的 header里面
 *
 * Created by Tang-QiFeng on 2019/4/14
 */
//@Configuration
//public class FeginInterceptor implements RequestInterceptor {
//
//    public static String TOKEN_HEADER = "token";
//
//    @Override
//    public void apply(RequestTemplate template) {
//        template.header(TOKEN_HEADER, getHeaders(getHttpServletRequest()).get(TOKEN_HEADER));
//    }
//
//    private javax.servlet.http.HttpServletRequest getHttpServletRequest() {
//        try {
//            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private Map<String, String> getHeaders(javax.servlet.http.HttpServletRequest request) {
//        Map<String, String> map = new LinkedHashMap<>();
//        Enumeration<String> enumeration = request.getHeaderNames();
//        while (enumeration.hasMoreElements()) {
//            String key = enumeration.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//
//}