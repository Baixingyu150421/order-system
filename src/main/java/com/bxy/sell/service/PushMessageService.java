package com.bxy.sell.service;

import com.bxy.sell.dto.OrderDTO;

/**
 * 微信模板消息推送
 */
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void OrderStatus(OrderDTO orderDTO);

}
