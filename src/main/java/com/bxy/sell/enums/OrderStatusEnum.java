package com.bxy.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"订单完结"),
    CANCEL(2,"取消订单")
    ;
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
