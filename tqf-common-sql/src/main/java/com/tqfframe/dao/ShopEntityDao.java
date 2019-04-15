package com.tqfframe.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.tqfframe.entity.ShopEntity;

@Mapper
public interface ShopEntityDao {

    List<ShopEntity> shoplist();

    int insert(@Param("pojo") ShopEntity pojo);

    int insertList(@Param("pojos") List< ShopEntity> pojo);

    int update(@Param("pojo") ShopEntity pojo);

}
