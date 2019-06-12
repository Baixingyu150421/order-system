package com.bxy.sell.service;

import com.bxy.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);
    //退款需要证书
    RefundResponse refund(OrderDTO orderDTO);
}
