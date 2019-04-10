package com.tqfframe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tang-QiFeng on 2019/3/3
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    /**
     * http://localhost:9090/api/tqf-phoneserver/imtqfphoneserver  经过eureka和zuul，实际访问是 http://localhost:7098/tqf-phoneserver/imtqfphoneserver
     * http://localhost:9090/api/phoneurl/imtqfphoneserver      通过zuul定义的反向代理进行访问
     */
    @RequestMapping("/imtqfphoneserver")
    public String imtqfphoneserver(){
        return "我是图片服务器:端口为"+port;
    }
}
