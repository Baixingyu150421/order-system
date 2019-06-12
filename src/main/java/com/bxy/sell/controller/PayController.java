package com.bxy.sell.controller;

import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.service.OrderService;
import com.bxy.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
        @Autowired
        private OrderService orderService;
        @Autowired
        private PayService payService;

        @GetMapping("/create")
        public ModelAndView create(@RequestParam("orderId") String orderId,
                                   @RequestParam("returnUrl") String returnUrl,
                                   Map<String,Object> map
                           ){

            //1.查询订单
            OrderDTO orderDTO = orderService.findOne(orderId);
            if(orderDTO == null){
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }

            //发起支付
            PayResponse payResponse = payService.create(orderDTO);
            //将参数注入到模板中
            map.put("payResponse",payResponse);
            map.put("returnUrl",returnUrl);
            return new ModelAndView("pay/create",map);
        }

    /**
     * 微信支付异步通知
     * @param noyifyData
     */
    @PostMapping("/notify")
        public ModelAndView notify(String noyifyData){
                payService.notify(noyifyData);

                //返回给微信处理结果 停止微信的异步通知
                return new ModelAndView("pay/success");
        }
}
