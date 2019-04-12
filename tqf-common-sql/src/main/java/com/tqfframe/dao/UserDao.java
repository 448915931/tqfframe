package com.tqfframe.dao;
import com.tqfframe.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Tang-QiFeng on 2019/3/4
 */
@Mapper
public interface UserDao {

    //查询用户
    UserEntity selectUserinfo(String username);
    //注册用户
    int inserUser(UserEntity user);
}
