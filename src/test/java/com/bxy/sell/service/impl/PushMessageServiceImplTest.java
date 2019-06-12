package com.bxy.sell.service.impl;

import com.bxy.sell.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {
    @Autowired
    private PushMessageServiceImpl pushMessage;
    @Autowired
    private OrderServiceImpl orderService;
    @Test
    public void test(){
        OrderDTO orderDTO = orderService.findOne("1555598276108527781");
        pushMessage.OrderStatus(orderDTO);
    }
}