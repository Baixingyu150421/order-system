package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Test
    @Ignore
    public void findOne() {
        ProductInfo one = productInfoService.findOne("1234567");
        Assert.assertEquals("1234567",one.getProductId());
    }

    @Test
    @Ignore
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> all = productInfoService.findAll(request);
        System.out.println(all.getTotalElements());

    }

    @Test
    @Ignore
    public void findUpAll() {
        List<ProductInfo> upAll = productInfoService.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    @Ignore
//    @Transactional
    public void saveProduct() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345678");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(4.55));
        productInfo.setProductStock(30);
        productInfo.setProductIcon("http://xxxx/icon.jpg");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("新品上市，欢迎品尝");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo productInfo1 = productInfoService.saveProduct(productInfo);
        Assert.assertNotNull(productInfo1);
    }
    @Test
    public void onSale(){
        ProductInfo productInfo = productInfoService.onSale("12345678");
        Assert.assertNotEquals(null,productInfo);
    }
}