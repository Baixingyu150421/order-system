package com.bxy.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"在架"),
    DOWN(1,"下架")
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code,String msg) {
        this.code = code;
        this.message = msg;
    }
}
