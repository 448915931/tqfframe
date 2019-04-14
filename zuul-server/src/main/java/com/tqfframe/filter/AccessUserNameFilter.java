package com.tqfframe.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 网关过滤
 */
public class AccessUserNameFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public String filterType() {
        return "pre";// 前置过滤器// 可以在请求被路由之前调用
    }

    /**
     *  经过zuul网关的接口，给原始接口加header
     *  作用：避免其他项目在认证完zuul的jwt认证后，进入项目接口再次进行security jwt认证的时候会空token。所以要给zuul加header！！！
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        if(token != null) {
//            System.out.println("token: " + token);
            ctx.addZuulRequestHeader("token",request.getHeader("token"));
        }
        System.out.println(String.format("%s AccessUserNameFilter request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }


}