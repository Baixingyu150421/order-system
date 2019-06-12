package com.bxy.sell.dataobject.mapper;

import com.bxy.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
//防止启动时找不到websocket相关依赖
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;
    @Test
    @Ignore
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","女生最爱");
        map.put("category_type",5);
        int res = mapper.insertByMap(map);
        Assert.assertEquals(1,res);
    }
    @Test
    @Ignore
    public void insertByObject(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("宝宝专属");
        category.setCategoryType(6);
        int res = mapper.insertByObject(category);
        Assert.assertEquals(1,res);
    }

    @Test
    @Ignore
    public void selectByCategory(){
        ProductCategory CategoryType = mapper.findByCategoryType(6);
        Assert.assertNotNull(CategoryType);
    }
    @Test
    @Ignore
    public void selectByCategoryName(){
        List<ProductCategory> categoryList = mapper.findByCategoryName("女生最爱");
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    @Ignore
    public void updateByCategoryType(){
        int result = mapper.updateByCategoryType("小朋友最爱", 3);
        Assert.assertEquals(1,result);
    }

    @Test
    @Ignore
    public void updateByObject(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("小宝宝专属");
        category.setCategoryType(6);
        int res = mapper.updateByObject(category);
        Assert.assertNotEquals(0,res);
    }

    @Test
    @Ignore
    public void deleteByCategoryType(){
        int res = mapper.deleteByCategoryType(6);
        Assert.assertNotEquals(0,res);
    }
    @Test
    public void selectByCategoryType(){
        ProductCategory category = mapper.selectByCategoryType(5);
        Assert.assertNotNull(category);
    }

}