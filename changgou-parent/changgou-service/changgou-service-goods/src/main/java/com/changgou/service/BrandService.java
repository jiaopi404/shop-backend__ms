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
}
