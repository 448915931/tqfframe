package com.tqfframe.api.test.controller;

import com.tqfframe.ResultUtil;
import com.tqfframe.api.test.service.CustomerService;
import com.tqfframe.common.BaseController;
import com.tqfframe.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Tang-QiFeng on 2019/4/7
 */
@RestController
@RequestMapping("/testapi")
@Api(value = "测试类管理", description = "测试类管理")
public class CustomerController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    /**
     * 熔断测试，消费者调用服务提供者
     * @param name
     * @return
     */
    @PostMapping(value = "/testhystrix")
    public ResultUtil testhystrix(@RequestParam(name = "name") String name){
        return customerService.testhystrix(name);
    }

}
