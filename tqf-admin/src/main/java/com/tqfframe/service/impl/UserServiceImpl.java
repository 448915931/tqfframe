package com.tqfframe.service.impl;

import com.tqfframe.dao.UserDao;
import com.tqfframe.service.UserServie;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Tang-QiFeng on 2019/3/8
 */
@Service
public class UserServiceImpl implements UserServie {
    @Resource
    private UserDao userDao;

    public String getuserid(){
        String id=  userDao.qulitycounts();
        return id;
    }
}
