package com.tqfframe.rest;


import com.tqfframe.JsonUtil;
import com.tqfframe.ResultUtil;
import com.tqfframe.domain.Customer;
import com.tqfframe.service.UserServie;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/produceapi")
public class ProduceController {

    private static Logger logger = LoggerFactory.getLogger(ProduceController.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    private UserServie userServie;

    /**
     * http://localhost:9090/swagger-ui.html
     *  PostMapping=RequestMapping+method = RequestMethod.Post
     *  这里使用 @RequestBody , 来接受前端传输过来的customer对象。
     * 测试控制器
     * @return
     */
    @ApiOperation(value = "post控制器",notes = "你好")
    @PostMapping(value = "/posttest", produces={"application/json;","text/html;charset=UTF-8;"})
    public ResultUtil posttest(@RequestBody Customer customer){
        System.out.println(customer.getId());
        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());
        String s = JsonUtil.obj2String(customer);
        System.out.println(s);
        return ResultUtil.ok();
    }

    /**
     * tqf-admin是项目名
     * http://localhost:9090/api/tqf-admin/produceapi/customer/1   经过eureka和zuul，实际访问是 http://localhost:9098/produceapi/customer/1
     * http://localhost:9090/api/adminurl/produceapi/customer/2      通过zuul定义的serviceId方式的反向代理进行访问,创建多个实例实现负载均衡
     * http://localhost:9090/api/fristurl/produceapi/customer/1   通过zuul定义的url方式的反向代理进行访问
     * @return
     */
    @ApiOperation(value = "服务提供者测试类", notes = "服务提供者测试类备注")
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getCustomer(@PathVariable int id) {
        String userid=userServie.getuserid();
        logger.info("日志记录");
        String aaa="我进来了，数据库获得:"+userid+"  我的id是"+id+"端口号为："+port;
        return aaa;

    }

    /**
     *
     * http://localhost:9090/api/tqf-admin/produceapi/hello   经过eureka和zuul，实际访问是 http://localhost:9098/produceapi/hello
     * http://localhost:9090/api/adminurl/produceapi/hello      通过zuul定义的serviceId方式的反向代理进行访问,创建多个实例实现负载均衡
     * @return
     */
    @ApiOperation(value = "你好啊",notes = "你好")
    @GetMapping(value = "hello", produces={"application/json;","text/html;charset=UTF-8;"})
    public ResultUtil index() {
        System.out.println("我是实例2，端口:"+port);
        return ResultUtil.ok();
    }
    /**
     * 测试控制器
     * @return
     */
    @ApiOperation(value = "postjson控制器",notes = "json测试")
    @PostMapping(value = "/postjsontest")
    public ResultUtil postjsontest(@RequestParam(name = "aaa") String aaa){
        System.out.println(aaa);
        return ResultUtil.ok();
    }

}