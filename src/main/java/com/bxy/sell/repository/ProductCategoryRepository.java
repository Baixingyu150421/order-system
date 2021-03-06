package com.bxy.sell.repository;

import com.bxy.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 实体类名+主键类型
 */
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory,Integer> {
        List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
