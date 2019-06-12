package com.bxy.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 返回给前端的信息
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不正确"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),

    ORDER_STATUS_ERROR(14,"订单状态不正确"),

    ORDER_UPDATE_FAIL(15,"订单更新失败"),

    OEDER_DETAIL_EMPTY(16,"订单详情为空"),

    PAY_STATUS_ERROR(17,"支付状态不正确"),

    PARAM_ERROR(1,"参数不正确"),

    CART_EMPTY(18,"购物车不能为空"),

    OPENID_EMPTY(19,"openid不能为空"),

    ORDER_OWNER_ERROR(20,"订单不属于当前用户"),

    WX_MP_ERROR(21,"微信公众号方面错误"),

    ORDER_CANCEL_SUCCESS(22,"订单取消成功"),

    ORDER_FINISH_SUCCESS(23,"订单完结成功"),

    PRODUCT_STATUS_ERROR(24,"商品状态异常"),

    LOGIN_FAIL(25,"登录错误，信息不正确"),

    LOGOUT_SUCCESS(26,"登出成功"),

    WXPAY_NOTIFY_MONRY_VERIFY_ERROR(27,"微信异步通知金额不一致")

    ;
    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
