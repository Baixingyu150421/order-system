package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.ProductCategory;
import com.bxy.sell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    ProductCategoryServiceImpl productCategoryService;
    @Test
    @Ignore
    public void findOne() {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    @Ignore
    public void findAll() {
        List<ProductCategory> categoryList = productCategoryService.findAll();
        System.out.println(categoryList);
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    @Ignore
    public void findByCategoryTypeIn() {
        List<Integer> typeList  = Arrays.asList(1,2,3);
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(typeList);

        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }


    @Test
    @Ignore
    @Transactional
    public void save() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("新品");
        category.setCategoryType(5);
        ProductCategory save = productCategoryService.save(category);
        Assert.assertNotEquals(new Integer(0), save.getCategoryId());




    }
}