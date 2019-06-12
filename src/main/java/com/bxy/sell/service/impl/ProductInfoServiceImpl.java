package com.bxy.sell.service.impl;

import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.dto.CartDTO;
import com.bxy.sell.enums.PayStatusEnum;
import com.bxy.sell.enums.ProductStatusEnum;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.repository.ProductInfoRepository;
import com.bxy.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoRepository infoRepository;
    /**
     * 查询单条商品信息
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        return infoRepository.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return infoRepository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        //添加枚举类
        return infoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo saveProduct(ProductInfo info) {
        return infoRepository.save(info);
    }

    /**
     * 添加库存
     * @param cartDTOList
     */
    @Override
    @Transactional  //要么全部扣除成功，要么全部失败
    public void increaseStock(List<CartDTO> cartDTOList) {
       for(CartDTO  cartDTO : cartDTOList){
           ProductInfo productInfo = infoRepository.findOne(cartDTO.getProductId());
           //若商品不存在
           if(productInfo == null){
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
           Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
           productInfo.setProductStock(result);
          infoRepository.save(productInfo);
       }
    }

    /**
     * 减库存
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            //减库存之前先查询
            ProductInfo productInfo = infoRepository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //开始减库存  可能会出现超卖 使用redis的锁机制
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            infoRepository.save(productInfo);
        }
    }

    /**
     * 上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = infoRepository.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return infoRepository.save(productInfo);
    }

    /**
     * 下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = infoRepository.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return infoRepository.save(productInfo);
    }
}
