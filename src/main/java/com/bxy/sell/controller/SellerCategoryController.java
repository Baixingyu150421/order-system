package com.bxy.sell.controller;

import com.bxy.sell.dataobject.ProductCategory;
import com.bxy.sell.enums.ResultEnum;
import com.bxy.sell.exception.SellException;
import com.bxy.sell.form.CategoryForm;
import com.bxy.sell.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 卖家类目
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
        @Autowired
        ProductCategoryService productCategoryService;

        /**
         * 商品类目
         * @param map
         * @return
         */
        @GetMapping("/list")
        public ModelAndView list(Map<String,Object> map){
            List<ProductCategory> categoryList = productCategoryService.findAll();
            map.put("categoryList",categoryList);
            return new ModelAndView("pay/category/list",map);
        }

    /**
     * 修改商品类目
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
        public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  Map<String, Object> map){

            if(categoryId != null){
                ProductCategory productCategory = productCategoryService.findOne(categoryId);
                map.put("Category",productCategory);
            }

            return new ModelAndView("pay/category/index",map);

        }

    /**
     * 更新类目
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
        @PostMapping("/save")
        public  ModelAndView save(@Valid CategoryForm form,
                                  BindingResult bindingResult,
                                  Map<String,Object> map
                                  ){
            //表单验证失败
            if(bindingResult.hasErrors()){
                map.put("msg",bindingResult.getFieldError().getDefaultMessage());
                map.put("url","/sell/seller/category/index");
                return new ModelAndView("pay/common/error",map);
            }
            ProductCategory productCategory = new ProductCategory();
            try{
                if(form.getCategoryId() != null){
                    productCategory = productCategoryService.findOne(form.getCategoryId());
                }
                BeanUtils.copyProperties(form,productCategory);
                productCategoryService.save(productCategory);
            }catch (SellException e){
                map.put("msg",e.getMessage());
                map.put("url","/sell/seller/category/index");
                return new ModelAndView("pay/common/error",map);
            }
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("pay/common/success",map);
        }
}
