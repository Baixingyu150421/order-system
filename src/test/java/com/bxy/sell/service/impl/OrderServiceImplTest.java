package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.OrderDetail;
import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.enums.OrderStatusEnum;
import com.bxy.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    private final String  BUYER_OPENID ="465465";
    private final String  ORDERID = "1555574880618567914";
    @Test
    @Ignore
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("校长");
        orderDTO.setBuyerAddress("沈阳师范大学");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("454652323");
        //购物车
        List<OrderDetail> orderDetailLists = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1234567");
        orderDetail.setProductQuantity(3);

        OrderDetail orderDetai2 = new OrderDetail();
        orderDetai2.setProductId("12345678");
        orderDetai2.setProductQuantity(2);

        orderDetailLists.add(orderDetail);
        orderDetailLists.add(orderDetai2);

        orderDTO.setOrderDetailList(orderDetailLists);
        OrderDTO result = orderService.create(orderDTO);
//        log.info("【创建订单】 result={}",result);
    }

    @Test
    @Ignore
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        System.out.println(orderDTO);
        log.info("【订单详情】 orderDTO = {}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    @Ignore
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    @Ignore
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    @Ignore
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
//    @Ignore
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDERID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESSED.getCode(),result.getPayStatus());
    }

    @Test
    public void findListAll(){
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有订单列表",orderDTOPage.getTotalElements()>0);
    }
}