package com.tqfframe.api.test.service.Impl;

import com.tqfframe.api.test.service.CustomerService;
import com.tqfframe.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Tang-QiFeng on 2019/4/7
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private UserDao userDao;

    @Override
    public void getTest() {
        System.out.println(userDao.qulitycounts());
    }
}
