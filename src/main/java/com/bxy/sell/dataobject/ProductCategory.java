package com.bxy.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 * product_category表
 * DynamicUpdate动态更新时间
 * 命名方式下划线改成驼峰
 */
@Entity
//实现更新数据是动态更新数据库修改时间
@DynamicUpdate
@Data
public class ProductCategory {
    /**类目id*/
    @Id
    @GeneratedValue
    private Integer categoryId;
    /*类目名称*/
    private String categoryName;
    /*类目编号*/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(){

    }
}
