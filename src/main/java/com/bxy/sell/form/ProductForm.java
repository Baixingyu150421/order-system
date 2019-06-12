package com.bxy.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 *商品添加表单数据封装
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

}
