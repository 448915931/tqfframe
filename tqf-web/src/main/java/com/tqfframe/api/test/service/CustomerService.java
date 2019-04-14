package com.tqfframe.api.test.service;

import com.tqfframe.ResultUtil;
import com.tqfframe.api.test.service.Impl.CustomerServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Tang-QiFeng on 2019/4/7
 */
@FeignClient(name= "tqf-admin", fallback = CustomerServiceImpl.class)
public interface CustomerService {
    /**
     * 测试熔断，服务提供者出问题进入，不出错则调用服务提供者接口
     */
    @PostMapping(value = "/produceapi/testhystrix")
    public ResultUtil testhystrix(@RequestParam(name = "name") String name);

}
