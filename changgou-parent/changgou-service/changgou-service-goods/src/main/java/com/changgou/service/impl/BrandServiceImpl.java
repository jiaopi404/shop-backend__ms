package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * 多条件搜索
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        // Example 自定义条件搜索对象
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    public Example createExample (Brand brand) {
        Example example = new Example(Brand.class); // Brand.class 当前操作对象的字节码
        Example.Criteria criteria = example.createCriteria(); // 条件构造器

        if (brand != null) {
            // 品牌名称 根据名字模糊搜索;
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            // 品牌图片地址
            if (!StringUtils.isEmpty(brand.getImage())) {
                criteria.andLike("image", "%" + brand.getImage() + "%");
            }
            // 品牌的首字母
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
            // 品牌id
            if(!StringUtils.isEmpty(brand.getId())){
                criteria.andEqualTo("id", brand.getId());
            }
            // 排序
            if(!StringUtils.isEmpty(brand.getSeq())){
                criteria.andEqualTo("seq", brand.getSeq());
            }
        }

        return example;
    }
}
