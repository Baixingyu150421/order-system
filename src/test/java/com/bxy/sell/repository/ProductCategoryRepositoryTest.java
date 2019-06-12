package com.bxy.sell.repository;

import com.bxy.sell.dataobject.ProductCategory;
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
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    @Ignore
    public void FindOneTest(){
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory);
    }
    //这个注解与service层的注解不同，service层的方法在抛出异常后会删除数据 ，这个测试完成后会直接回滚
    @Test
    @Ignore
    @Transactional
    public void  saveTest(){
        ProductCategory category = new ProductCategory();
        category.setCategoryId(3);
        category.setCategoryName("男生最爱");
        category.setCategoryType(3);
        repository.save(category);
    }
    @Test
    @Ignore
    public void findByCategoryTypeIn(){
        //包含查询
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }

}