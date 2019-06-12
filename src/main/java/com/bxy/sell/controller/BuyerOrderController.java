package com.bxy.sell.controller;

import com.bxy.sell.ViewObject.ResultVO;
import com.bxy.sell.converter.OrderForm2OrderDTOConverter;
import com.bxy.sell.dto.OrderDTO;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.form.OrderForm;
import com.bxy.sell.service.BuyerService;
import com.bxy.sell.service.OrderService;
import com.bxy.sell.service.WebSocket;
import com.bxy.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 ，orderForm={}" ,orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> modelMap = new HashMap<>();
        modelMap.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(modelMap);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size) {

        if(StringUtils.isEmpty(openid)){
            log.error("【订单查询列表】 用户openid为空");
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }
        PageRequest request = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }
    //查询订单(订单详情)
    @GetMapping("/detail")
    public ResultVO<OrderDTO> Detail( @RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId

    ){
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】 openid或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId

    ){
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【查询订单详情】 openid或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
