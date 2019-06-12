package com.bxy.sell.repository;

import com.bxy.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;
    
    @Test
    @Ignore
    public void findOne(){
        ProductInfo productInfo = productInfoRepository.findOne(new String("1234567"));
        System.out.println(productInfo);
    }
    @Test
    @Ignore
    public void findAll(){
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }
    @Test
    @Ignore
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234567");
        productInfo.setProductName("包子");
        productInfo.setProductPrice(new BigDecimal(3.55));
        productInfo.setProductStock(15);
        productInfo.setProductIcon("http://xxxx/icon.jpg");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("新品上市，欢迎品尝");
        productInfo.setProductStatus(0);
        ProductInfo save = productInfoRepository.save(productInfo);
        Assert.assertNotNull(save);
    }
    @Test
    @Ignore
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0,byProductStatus.size());
    }
}