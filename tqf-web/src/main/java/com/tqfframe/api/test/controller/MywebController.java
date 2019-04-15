package com.tqfframe.api.test.controller;

import com.tqfframe.ResultUtil;
import com.tqfframe.common.BaseController;
import com.tqfframe.entity.ShopEntity;
import com.tqfframe.service.ShopEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tang-QiFeng on 2019/4/14
 */
@Api(value = "MybitsCodeHepler自动构建结构类", description = "MybitsCodeHepler自动构建结构类")
@RestController
@RequestMapping("mybitsapi")
public class MywebController extends BaseController {

    @Autowired
    private ShopEntityService shopEntityService;

    @ApiOperation(value = "查询列表",notes="查询列表")
    @GetMapping("/shoplist")
    public ResultUtil shoplist(){
       Map<String,Object> map= new HashMap<String,Object>();
        List<ShopEntity> list= shopEntityService.shoplist();
        map.put("list",list);
        return ResultUtil.ok(map);
    }
    @ApiOperation(value = "添加数据",notes="添加数据")
    @PostMapping(value = "/insertshop", produces={"application/json;","text/html;charset=UTF-8;"} )
    public ResultUtil insertshop(@RequestParam(name = "name") String name,@RequestParam(name = "price") Integer price){
        System.out.println();
        ShopEntity shopEntity=new ShopEntity();
        shopEntity.setShopname(name);
        shopEntity.setShopprice(price);
        shopEntityService.insert(shopEntity);
        return ResultUtil.ok();
    }
}
