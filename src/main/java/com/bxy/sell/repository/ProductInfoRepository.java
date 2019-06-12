package com.bxy.sell.repository;

import com.bxy.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息dao
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /**
     * 根据商品状态查询上架的商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus (Integer productStatus );

}
