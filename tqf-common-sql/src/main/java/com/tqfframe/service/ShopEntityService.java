package com.tqfframe.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.tqfframe.entity.ShopEntity;
import com.tqfframe.dao.ShopEntityDao;

@Service
public class ShopEntityService{

    @Resource
    private ShopEntityDao shopEntityDao;

    public List<ShopEntity> shoplist(){
        return shopEntityDao.shoplist();
    }

    public int insert(ShopEntity pojo){
        System.out.println(pojo.toString());
        return shopEntityDao.insert(pojo);
    }

    public int insertList(List<ShopEntity> pojos){
        return shopEntityDao.insertList(pojos);
    }

    public int update(ShopEntity pojo){
        return shopEntityDao.update(pojo);
    }

}
