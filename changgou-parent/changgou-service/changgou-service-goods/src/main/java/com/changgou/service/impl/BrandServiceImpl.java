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

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Override
    public Brand findById(int id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加品牌
     * @param brand
     * @return 创建的 id
     */
    @Override
    public int add(Brand brand) {
        return brandMapper.insert(brand);
    }

    /**
     * 更新品牌
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 删除 品牌
     * @param brandId Integer Brand.id
     */
    @Override
    public void delete(Integer brandId) {
        brandMapper.deleteByPrimaryKey(brandId);
    }
}
