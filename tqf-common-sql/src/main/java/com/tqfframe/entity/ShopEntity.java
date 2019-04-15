package com.tqfframe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@AllArgsConstructor//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
public class ShopEntity {
    private Integer id;
    private String shopname;
    private Integer shopprice;

    @Override
    public String toString() {
        return "ShopEntity{" +
                "id=" + id +
                ", shopname='" + shopname + '\'' +
                ", shopprice=" + shopprice +
                '}';
    }
}
