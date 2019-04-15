package com.tqfframe.api.test.service.Impl;

import com.tqfframe.ResultUtil;
import com.tqfframe.api.test.service.CustomerService;
import com.tqfframe.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Tang-QiFeng on 2019/4/7
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public ResultUtil testhystrix(@RequestParam(name = "name") String name) {
        return ResultUtil.error(500,"服务提供者出现故障，进入熔断处理！！！");
    }
}
