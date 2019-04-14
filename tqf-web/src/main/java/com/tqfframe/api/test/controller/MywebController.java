package com.tqfframe.api.test.controller;

import com.tqfframe.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tang-QiFeng on 2019/4/14
 */
@Api(value = "Mybits缓存测试类", description = "Mybits缓存测试类")
@RestController
@RequestMapping("mybitsapi")
public class MywebController {

    @ApiOperation(value = "测试一级缓存接口",notes="测试一级缓存接口")
    @RequestMapping("/testmybits")
    public ResultUtil testmybits(){

        return ResultUtil.ok("测试一级缓存接口完成");
    }
}
