# tqfframe
springboot2.0+Springcloud+zuul+Spring Security+jwt+eureka+mybits+redis+mongodb+solr+es+Swagger2 等

### springBoot 基础框架
1. eureka 服务与服务的注册中心
2. Feign 负责服务间的调用
3. zuul 向外暴露的服务网关
4. Spring Security 安全框架

### 可用idea插件
1. lombok插件，实现@get @set @AllArgsConstructor注入，代码更简洁！
2. MybatisCodeHelper插件 ，实现mybatis自动化构建！

### 环境
jdk1.8以上，mysql，redis

### 各种地址
1. swagger2地址   http://localhost:9090/swagger-ui.html
1. eureka服务器地址http://localhost:8761/

### 各种接口
1. eureka                        eureka                                               端口 8761
2. zuul                          zuul代理                                             端口 9090 
3. auth-center                   Security用户认证中心，登录注册接口。                 端口 9095
4. tqf-web                       服务消费端   web服务的接口 项目名称tqf-web           端口 9096
5. tqf-admin                     服务提供端   项目名称tqf-admin                       实例1：9097 实例2：9098
6. tqf-escloud                   es服务提供端 项目名称tqf-escloud                     端口 9094   
7. tqf-phoneserver               图片服务器   项目名称tqf-phoneserver                 端口 9099 
8. tqf-common-sql  sql           公共方法module，用于当作其他项目的sql依赖
9. tqf-common-util util          公共方法module，用于当作其他项目的util工具依赖(目前有RedisUtil等)
10. tqf-common-jwt                jwt是公共的jwt认证module.用于其他项目的Security依赖
11. tqf-admin2 tqf-admin3。。。  根据具体业务，配置多个服务提供端项目

### 项目解释
tqf-web 服务消费端提供一个web端接口访问的入口，使用Feign去调取其他提供端微服务接口，配置熔断处理。

### 负载均衡
1. 只需要复制一份代码，修改端口名部署即可

### 项目启动运行
1. 先启动eureka服务器
2. 再启动zuul服务器
3. 再启动其他项目。
4. 去auth-center中注册用户，然后登录用户，其他用户用登录后得到的token去进行接口访问













