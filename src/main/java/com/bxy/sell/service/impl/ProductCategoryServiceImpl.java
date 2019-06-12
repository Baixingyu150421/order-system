package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.ProductCategory;
import com.bxy.sell.repository.ProductCategoryRepository;
import com.bxy.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目service
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository repository ;

    /**
     * 查询单条类目信息
     * @param categoryId
     * @return
     */
    @Override
    public ProductCategory findOne(Integer categoryId) {
        ProductCategory category = repository.findOne(categoryId);
        return category;
    }

    /**
     * 查询所有类目信息
     * @return
     */
    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> categoryList = repository.findAll();
        return categoryList;
    }

    /**
     * 查询包含的类目信息
     * @param categoryTypeList
     * @return
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        List<ProductCategory> productCategoryTypeList = repository.findByCategoryTypeIn(categoryTypeList);
        return productCategoryTypeList;
    }

    /**
     * 新增类目信息
     * @param productCategory
     * @return
     */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        ProductCategory category = repository.save(productCategory);
        return category;
    }
}
