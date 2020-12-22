package com.changgou.service;

import com.changgou.goods.pojo.Brand;

import java.util.List;

public interface BrandService {
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
}
