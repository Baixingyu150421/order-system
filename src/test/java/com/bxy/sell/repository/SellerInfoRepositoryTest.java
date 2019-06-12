package com.bxy.sell.repository;

import com.bxy.sell.dataobject.SellerInfo;
import com.bxy.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Test
    @Ignore
    public void test(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setUsername("admin");
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
//    @Ignore
    public void findByOpenid() {

        SellerInfo res = sellerInfoRepository.findByOpenid("abc");
        Assert.assertEquals("abc",res.getOpenid());
    }
}