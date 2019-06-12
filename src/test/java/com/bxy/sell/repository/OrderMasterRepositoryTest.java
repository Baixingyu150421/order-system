package com.bxy.sell.repository;

import com.bxy.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    @Test
    @Ignore
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("4684689");
        orderMaster.setBuyerAddress("沈阳师范大学");
        orderMaster.setBuyerName("李先生");
        orderMaster.setBuyerOpenid("1465468vs54545");
        orderMaster.setBuyerPhone("12345678954");
        orderMaster.setOrderAmount(new BigDecimal(6.6));
        OrderMaster save = repository.save(orderMaster);
        Assert.assertNotNull(save);
    }


    @Test
    @Ignore
    public void finfOne(){
        OrderMaster orderMaster = repository.findOne("4684689");
        System.out.println(orderMaster);
        Assert.assertNotNull(orderMaster);
    }


    @Test
    @Ignore
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("4684689", request);
        System.out.println(orderMasterPage.getTotalElements());
        Assert.assertNotNull(orderMasterPage);
    }

}