package com.tqfframe.dao;
import com.tqfframe.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Tang-QiFeng on 2019/3/4
 */
@Mapper
public interface UserDao {

    String qulitycounts();
    //查询用户是否存在
    UserEntity selectUserinfo(String username);
    //注册用户
    int inserUser(UserEntity user);
}
