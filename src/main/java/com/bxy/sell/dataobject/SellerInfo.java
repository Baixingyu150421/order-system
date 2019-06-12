package com.bxy.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息表
 */
@Entity
@Data
public class SellerInfo {
    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

}
