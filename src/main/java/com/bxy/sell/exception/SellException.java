package com.bxy.sell.exception;


import com.bxy.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * 统一异常处理
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException( Integer code,String message) {
        super(message);
        this.code = code;
    }
}
