package com.tqfframe.service;

import com.tqfframe.dao.UserDao;
import com.tqfframe.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Collections.emptyList;

/**
 * Created by Tang-QiFeng on 2019/4/10
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.selectUserinfo(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
    }
}
