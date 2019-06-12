package com.bxy.sell.service;

import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品信息service
 */
public interface ProductInfoService {
        //查询单条商品记录
        ProductInfo findOne(String productId);
        //查询所有商品记录(卖家端分页)
        Page<ProductInfo> findAll(Pageable pageable);
        //查询所有上架商品(买家端)
        List<ProductInfo> findUpAll();
        //添加商品信息(卖家端)
        ProductInfo saveProduct(ProductInfo info);
        //添加库存
        void increaseStock(List<CartDTO> cartDTOList);
        //减库存
        void decreaseStock(List<CartDTO> cartDTOList);
        //商品上架
        ProductInfo onSale(String productId);
        //商品下架
        ProductInfo offSale(String productId);
 }
