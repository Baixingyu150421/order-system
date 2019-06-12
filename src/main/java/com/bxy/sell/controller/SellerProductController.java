package com.bxy.sell.controller;

import com.bxy.sell.dataobject.ProductCategory;
import com.bxy.sell.dataobject.ProductInfo;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.form.ProductForm;
import com.bxy.sell.service.ProductCategoryService;
import com.bxy.sell.service.ProductInfoService;
import com.bxy.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    ProductInfoService infoService;
    @Autowired
    ProductCategoryService categoryService;
    /**
     * 查询商品详情
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            Map<String,Object> map
    ){
        PageRequest request = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage = infoService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("pay/product/list",map);
    }

    /**
     * 上架
      * @param productId
     * @param map
     * @return
     */
    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map
                               ){
        try{
            infoService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","sell/seller/product/list");
            return new ModelAndView("/pay/common/error",map);
        }
        map.put("url","sell/seller/product/list");
        return new ModelAndView("pay/common/list",map);
    }


    /**
     * 下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map
    ){
        try{
            infoService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","sell/seller/product/list");
            return new ModelAndView("pay/common/error",map);
        }
        map.put("url","sell/seller/product/list");
        return new ModelAndView("pay/common/success",map);
    }

    /**
     * 新增商品信息
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = infoService.findOne(productId);
            map.put("productInfo" ,productInfo );
        }
        //查询类目信息
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("pay/product/index",map);
    }

    /**
     * 更新/保存
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
//    @CachePut(cacheNames = "product",key = "123")
    //驱逐缓存，在执行方法之后
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map
                             ){
            if(bindingResult.hasErrors()){
                map.put("msg",bindingResult.getFieldError().getDefaultMessage());
                map.put("url","/sell/seller/product/index");
                return new ModelAndView("pay/common/error",map);
            }
            ProductInfo productInfo = new ProductInfo();
            try{
                //如果不是新增商品
                if(!StringUtils.isEmpty(form.getProductId())){
                     productInfo = infoService.findOne(form.getProductId());
                }else{
                    productInfo.setProductId(KeyUtil.genUniqueKey());
                }
                BeanUtils.copyProperties(form,productInfo);
                infoService.saveProduct(productInfo);
            }catch (SellException e){
                map.put("msg",e.getMessage());
                map.put("url","/sell/seller/product/index");
                return new ModelAndView("pay/common/error",map);
            }
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("pay/common/success",map);
    }
}
