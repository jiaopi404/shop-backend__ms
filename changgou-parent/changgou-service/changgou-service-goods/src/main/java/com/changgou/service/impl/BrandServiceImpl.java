package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Brand> findAll() {
        // 查询所有 -> 通用Mapper.selectAll()
        return brandMapper.selectAll(); // BrandMapper 继承了通用 Mapper
    }
}
