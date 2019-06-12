package com.bxy.sell.controller;

import com.bxy.sell.ViewObject.ProductInfoVO;
import com.bxy.sell.ViewObject.ProductVO;
import com.bxy.sell.ViewObject.ResultVO;
import com.bxy.sell.dataobject.ProductCategory;
import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.service.ProductCategoryService;
import com.bxy.sell.service.ProductInfoService;
import com.bxy.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//返回Json格式
@RestController
@RequestMapping(value = "/buyer/product/")
public class BuyerProductController {
        @Autowired
        private ProductInfoService infoService;
        @Autowired
        private ProductCategoryService productCategoryService;


        @GetMapping(value = "/list")
        @Cacheable(cacheNames = "product",key = "123")
        public ResultVO getUpInfoList(){
            //1.查询所有上架的商品
            List<ProductInfo> productInfoList = infoService.findUpAll();

            List<Integer> list = new ArrayList<>();
            for(ProductInfo  lists: productInfoList){
                list.add(lists.getCategoryType());
            }
            //(精简写法 )
//            List<Integer> categroyList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
            //2.获取商品类目
            List<ProductCategory> CategoryTypeList = productCategoryService.findByCategoryTypeIn(list);
            //3.拼接数据
            List<ProductVO> productVOS = new ArrayList<>();
            for(ProductCategory category : CategoryTypeList ){
                ProductVO productVO = new ProductVO();
                productVO.setCategoryName(category.getCategoryName());
                productVO.setCategoryType(category.getCategoryType());

                List<ProductInfoVO> productInfoVOS = new ArrayList<>();
                for(ProductInfo infoVO: productInfoList){
                    if(infoVO.getCategoryType().equals(category.getCategoryType())){
                        ProductInfoVO productInfoVO = new ProductInfoVO();
                        BeanUtils.copyProperties(infoVO,productInfoVO);
                        productInfoVOS.add(productInfoVO);
                    }
                }
                productVO.setProductInfoVOList(productInfoVOS);
                productVOS.add(productVO);
            }
              return ResultVOUtil.success(productVOS);
        }
}
