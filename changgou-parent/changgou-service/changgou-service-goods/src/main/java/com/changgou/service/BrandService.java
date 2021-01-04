package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    /**
     * 根据 分类id 查询品牌集合
     */
    List<Brand> findBrandByCategoryId(Integer categoryId);

    // 不需要写 DAO, 直接写 service

    /**
     * 查询所有
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据 id 获取 brand
     * @param id
     * @return
     */
    Brand findById(int id);

    /**
     * 增
     * @param brand Brand
     */
    int add(Brand brand);

    /**
     * 改
     * @param brand
     */
    void update(Brand brand);

    /**
     * 删
     * @param brandId Integer Brand.id
     */
    void delete(Integer brandId);

    /**
     * 多条件搜索品牌
     * @param brand
     * @return
     */
    List<Brand> findList (Brand brand);

    /**
     * 分页
     * @param pageNum 分页
     * @param pageSize 分页
     */
    PageInfo<Brand> findPage (Integer pageNum, Integer pageSize);

    /**
     * 条件 + 分页 查询
     */
    PageInfo<Brand> findPage (Brand brand, Integer pageNum, Integer pageSize);
}
