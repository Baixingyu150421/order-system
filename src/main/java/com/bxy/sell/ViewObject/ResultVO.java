package com.bxy.sell.ViewObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 */
@Data
//属性为null的时候不返回
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T>  implements Serializable {
    //快捷键 ctrl+shift+L
    private static final long serialVersionUID = 8336750344137188315L;
    /* 错误码 */
    private Integer code;
    /* 提示信息 */
    private String msg;
    /* 返回数据 */
    private T data;

}
