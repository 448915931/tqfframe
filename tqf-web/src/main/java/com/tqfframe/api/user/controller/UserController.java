package com.tqfframe.api.user.controller;

import com.tqfframe.ResultUtil;
import com.tqfframe.common.BaseController;
import com.tqfframe.entity.UserEntity;
import com.tqfframe.exception.UsernameIsExitedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Api(value = "用户管理", description = "用户管理")
public class UserController extends BaseController {
    /**
     * 注册用户
     * @param user
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/registe")
    public ResultUtil registe(@RequestBody UserEntity user) {
        UserEntity bizUser =  userDao.selectUserinfo(user.getUsername());
        if(null != bizUser){
            throw new UsernameIsExitedException("用户已经存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        int result=userDao.inserUser(user);
        if(result==1){
            return ResultUtil.ok();
        } else {
            return ResultUtil.error("注册失败");
        }

    }
}
