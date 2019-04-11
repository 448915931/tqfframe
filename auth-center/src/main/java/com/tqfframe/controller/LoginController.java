package com.tqfframe.controller;

import com.tqfframe.ResultUtil;
import com.tqfframe.constant.ConstantKey;
import com.tqfframe.dao.UserDao;
import com.tqfframe.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(description = "用户登录controller")
public class LoginController {

    @Resource
    private UserDao userDao;

    @ApiOperation(value = "自定义登录")
    @PostMapping(value = "/login", produces={"application/json;","text/html;charset=UTF-8;"})
    public ResultUtil login(@RequestBody UserEntity user,HttpServletResponse response) {
        UserEntity userVo = userDao.selectUserinfo(user.getUsername());
        System.out.println(userVo.getPassword());
        if (userVo != null) {
            // 这里可以根据用户信息查询对应的角色信息，这里为了简单，我直接设置个空list
            List roleList = new ArrayList<>();
            String subject = userVo.getUsername() + "-" + roleList;
            String token = Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)) // 设置过期时间 365 * 24 * 60 * 60秒(这里为了方便测试，所以设置了1年的过期时间，实际项目需要根据自己的情况修改)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
            return ResultUtil.ok("Bearer " + token);
        }
        return ResultUtil.error("登录失败");
    }

    @ApiOperation(value = "获取登录人的授权信息")
    @GetMapping("/get")
    @Secured("ROLE_ADMIN")
    @ResponseBody
    public Authentication get() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
