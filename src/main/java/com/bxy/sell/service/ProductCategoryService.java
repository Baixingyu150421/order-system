package com.bxy.sell.service;

import com.bxy.sell.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    //查询一条
    ProductCategory findOne(Integer categoryId);
    //查询所有
    List<ProductCategory> findAll();
    //查询类目集合
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    //添加类目
    ProductCategory save(ProductCategory productCategory);
}
