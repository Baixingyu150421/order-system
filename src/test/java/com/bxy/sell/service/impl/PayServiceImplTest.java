package com.bxy.sell.service.impl;

import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.service.OrderService;
import com.bxy.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    PayService payService;
    @Autowired
    OrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1555598276108527781");
        payService.create(orderDTO);
    }
}
