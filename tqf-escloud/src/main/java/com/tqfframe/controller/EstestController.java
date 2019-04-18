package com.tqfframe.controller;

import com.tqfframe.ResultUtil;
import com.tqfframe.service.EstestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tang-QiFeng on 2019/4/17
 */
@RestController
@RequestMapping("/esapi")
@Api(value = "es类管理", description = "es类管理")
public class EstestController extends BaseController{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(EstestController.class);

    @Autowired
    private EstestService estestService;


    @GetMapping("/createmapper")
    @ApiOperation(value = "创建es索引",notes="创建es索引")
    public ResultUtil createmapper(){
        System.out.println("1111");
        estestService.createmapper();
        return ResultUtil.ok();
    }


    @GetMapping(value = "/insertdoc")
    @ApiOperation(value = "添加数据",notes="添加数据")
    public String insertdoc() {
        estestService.saveALLByList();
        return "新增成功";
    }
}
