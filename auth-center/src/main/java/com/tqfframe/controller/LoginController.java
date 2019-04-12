package com.tqfframe.controller;

import com.tqfframe.RedisUtil;
import com.tqfframe.ResultUtil;
import com.tqfframe.constant.ConstantKey;
import com.tqfframe.dao.UserDao;
import com.tqfframe.entity.UserEntity;
import com.tqfframe.exception.UsernameIsExitedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Api(description = "用户登录controller")
public class LoginController  extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "自定义登录")
    @PostMapping(value = "/login", produces={"application/json;","text/html;charset=UTF-8;"})
    public ResultUtil login(@RequestBody UserEntity user,HttpServletResponse response) {
        UserEntity userVo = userDao.selectUserinfo(user.getUsername());
        if (userVo != null) {
            //密码校验
            if (bCryptPasswordEncoder.matches(user.getPassword(), userVo.getPassword())){
                // 这里可以根据用户信息查询对应的角色信息，这里为了简单，我直接设置个空list
                List roleList = new ArrayList<>();
                logger.info("用户权限"+userVo.getAuthority());
                logger.info("用户地址"+userVo.getCity());
                roleList.add(userVo.getUsername());
                roleList.add(userVo.getAuthority());
                roleList.add(userVo.getCity());
                String subject = userVo.getUsername() + "-" + roleList;
                String token = Jwts.builder()
                        .setSubject(subject)
                        .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 设置过期时间10分钟
                        .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .compact();
                // 登录成功后，返回token到header里面
                response.addHeader("token", "Bearer " + token);
                //把token放入list
                redisUtil.lSet("token","Bearer " + token);
                return ResultUtil.ok("Bearer " + token);
            }
            return ResultUtil.error("密码错误");
        }
        return ResultUtil.error("用户为空");
    }


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
    //            return ResultUtil.error("用户已经存在");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            int result=userDao.inserUser(user);
            if(result==1){
                return ResultUtil.ok();
            }
        return ResultUtil.error("注册失败");
    }

    /**
     *      因为使用拦截器进行注销，所以访问http://localhost:9090/logout 加header进行GET请求注销
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "注销用户")
    @GetMapping("/userlogout")
    public ModelAndView userlogout(HttpServletRequest request, HttpServletResponse response) {
        //删除token缓存
        redisUtil.del("token"+ request.getHeader("token"));
        //获取的Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        logger.info("注销成功了!!!!");
//        return ResultUtil.ok("注销成功");
        return new ModelAndView("/index");  //跳转到退出页面
    }



    @ApiOperation(value = "获取登录人的授权信息")
    @GetMapping("/get")
    @ResponseBody
    public Authentication get() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @ApiOperation(value = "查询用户权限")
    @GetMapping("/authorityList")
    public List<String> authorityList(){
        List<String> authentication = getAuthentication();
        return authentication;
    }

    @ApiOperation(value = "指定不用认证的一个接口")
    @GetMapping("/index")
    public ResultUtil index(){
        System.out.println("指定不用认证的一个接口");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication == null){
            System.out.println("已经是空Authentication了");
        }
         return ResultUtil.ok("注销成功");
    }


}
