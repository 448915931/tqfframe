# tqfframe
springboot2.0+Springcloud+zuui+eureka+mybits+redis+mongodb+solr+es+Swagger2 等

##环境
jdk1.8以上，mysql，redis

### 各种地址
1. swagger2地址   http://localhost:9090/swagger-ui.html
1. eureka服务器地址http://localhost:8761/

### 各种接口
1. eureka          eureka  服务器端口 8761
2. zuul            zuul代理   端口 9090 
3. tqf-web         服务消费端 ，web服务的接口 端口9096  项目名称tqf-web
4. tqf-admin       服务提供端 实例1：9097 实例2：9098  项目名称tqf-admin
5. tqf-phoneserver 图片服务器  端口 9099  项目名称tqf-phoneserver
6. tqf-common-sql  sql  module项目，用于当作其他项目的sql依赖
7. tqf-common-util util 公共方法module，用于当作其他项目的util工具依赖
8. tqf-common-jwt  jwt是公共的jwt认证module.用于其他项目的Security依赖

### 负载均衡
1. 只需要复制一份代码，修改端口名部署即可