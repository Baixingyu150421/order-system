package com.bxy.sell.controller;

import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    OrderService orderService;



    /**
     * 订单列表
     * @param page 第几页，从第一页开始
     * @param size 每个多少条数据
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1" ) Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){

        PageRequest request = new PageRequest(page - 1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        return new ModelAndView("pay/order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId ,
                               Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
             orderService.cancel(orderDTO);
        }catch (SellException e){
            log.error("【卖家取消订单】 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("pay/common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("pay/common/success",map);
    }

    /**
     * 卖家查询订单明细
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId ,
                               Map<String,Object> map
    ){
        OrderDTO orderDTO = new OrderDTO();
        try{
             orderDTO = orderService.findOne(orderId);

        }catch (SellException e){
            log.error("【卖家查询订单明细】 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("pay/common/error",map);
        }
//        map.put("msg",ResultEnum.SUCCESS.getCode());
        map.put("orderDTO",orderDTO);
        return new ModelAndView("pay/order/detail",map);
    }

    /**
     * 卖家端完结订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                                Map<String,Object> map
                               ){
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("【卖家端完结订单】 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("pay/common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("pay/common/success",map);
    }
}
