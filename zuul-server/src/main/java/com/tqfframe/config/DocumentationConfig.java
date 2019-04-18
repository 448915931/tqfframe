package com.tqfframe.config;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Tang-QiFeng on 2019/4/7
 */
@EnableSwagger2
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    //zuul的路由信息
    private final RouteLocator routeLocator;

    public DocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    /**
     *  访问  http://localhost:9090/swagger-ui.html   的时候促发
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
//        for (Route route:routes){
//            System.out.println(route.getId());
//            System.out.println(route.getFullPath());
//            System.out.println(route.getLocation());
//        }
        //在这里遍历的时候，可以排除掉敏感微服务的路由
        routes.forEach(
            route -> {
                //剔除掉相同poi文档
                if(!route.getId().equals(route.getLocation())){
                    resources.add(
                            swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"),"2.0")
                    );
                }
            }
        );
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        //修改下列列表名称
        switch(name){
            case "authcenterurl" :
                name="jwt注册中心";
                break;
            case "adminurl" :
                name="服务提供端";
                break;
            case "webapiurl" :
                name="web消费端";
                break;
            case "phoneurl" :
                name="图片服务器";
                break;
            case "escloudurl" :
                name="es服务器";
                break;
            default :
                name="";
        }
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}