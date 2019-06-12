package com.bxy.sell.dataobject.mapper;

import com.bxy.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 使用mybatis注解版实现对商品类目表的增删改查
 */
public interface ProductCategoryMapper {
    //第一种方式通过Mapper
    @Insert("insert into product_category (category_name, category_type) " +
            " values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER}) ")
    int insertByMap(Map<String,Object> map);


    //第二种方式使用Object
    @Insert("insert into product_category (category_name, category_type) " +
            " values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER}) ")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType,jdbcType=INTEGER}")
    //把查询出来的结果映射成对象
    @Results({
            @Result(column = "category_id" ,property = "categoryId"),
            @Result(column = "category_name" ,property = "categoryName"),
            @Result(column = "category_type" ,property = "categoryType")
    })
    ProductCategory  findByCategoryType(Integer categoryType);


    //不是单条数据时
    @Select("select * from product_category where category_Name = #{categoryName,jdbcType=VARCHAR}")
    //把查询出来的结果映射成对象
    @Results({
            @Result(column = "category_id" ,property = "categoryId"),
            @Result(column = "category_name" ,property = "categoryName"),
            @Result(column = "category_type" ,property = "categoryType")
    })
    List<ProductCategory>  findByCategoryName(String categoryName);

    //更新
    @Update("update product_category set category_name = #{categoryName,jdbcType=VARCHAR} " +
            "where category_type = #{categoryType,jdbcType=INTEGER}")
    int updateByCategoryType(@Param("categoryName")String categoryName ,
                             @Param("categoryType") Integer categoryType);



    //通过对象去更新
    @Update("update product_category set category_name = #{categoryName,jdbcType=VARCHAR} " +
            "where category_type = #{categoryType,jdbcType=INTEGER}")
    int updateByObject(ProductCategory productCategory);

    //删除
    @Delete("delete from product_category where category_type = #{categoryType , jdbcType=INTEGER}")
    int deleteByCategoryType(Integer categoryType);

    //通过categoryType查询
    ProductCategory selectByCategoryType(Integer categoryType);
}
