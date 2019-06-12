package com.bxy.sell.repository;

import com.bxy.sell.dataobject.OrderDetail;
import com.bxy.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    OrderDetailRepository repository;
    @Test
    @Ignore
    public void save(){
       OrderDetail orderDetail = new OrderDetail();
       orderDetail.setDetailId("65465465");
       orderDetail.setOrderId("1435435");
       orderDetail.setProductId("1353555");
       orderDetail.setProductIcon("http://xxx.jpg");
       orderDetail.setProductName("鸡蛋饼");
       orderDetail.setProductPrice(new BigDecimal(22));
       orderDetail.setProductQuantity(55);
       OrderDetail orders = repository.save(orderDetail);
       Assert.assertNotNull(orders);
    }

    @Test
//    @Ignore
    public void findByOrderId() {
        List<OrderDetail> orderList = repository.findByOrderId("1435435");
        Assert.assertNotNull(orderList);
    }
}