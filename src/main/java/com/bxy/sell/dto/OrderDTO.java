package com.bxy.sell.dto;

import com.bxy.sell.dataobject.OrderDetail;
import com.bxy.sell.enums.OrderStatusEnum;
import com.bxy.sell.enums.PayStatusEnum;
import com.bxy.sell.utils.EnumUtil;
import com.bxy.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输类 一个订单记录对应多个订单详情
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;
    /* 买家姓名*/
    private String buyerName;
    /* 买家电话*/
    private String buyerPhone;
    /* 买家地址*/
    private String buyerAddress;
    /* 买家微信openid*/
    private String buyerOpenid;
    /* 订单总金额*/
    private BigDecimal orderAmount;
    /* 订单状态 默认0新下单*/
    private Integer orderStatus;
    /* 支付状态 默认0未支付*/
    private Integer payStatus;
    /* 创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /* 更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /* 订单明细*/
    private List<OrderDetail> orderDetailList;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
