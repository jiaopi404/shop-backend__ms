package com.changgou.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * DAO 使用通用 Mapper, Dao 接口需要继承  tk.mybatis.mapper.common.Mapper
 *   增加数据: 调用 Mapper.insert(), or Mapper.insertSelective()
 *
 *   修改: Mapper.update(T), or Mapper.updateByPrimaryKey(T)
 *
 *   查询: Mapper.selectByPrimaryKey(id)
 *   条件查询: Mapper.select(T) // T JavaBean 对象
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 根据 分类id 查询 brand 集合;
     * @param categoryId 分类 id
     */
    @Select("SELECT*FROM tb_brand tb LEFT JOIN tb_category_brand tcb ON tb.id=tcb.brand_id WHERE tcb.category_id=#{categoryId};")
    List<Brand> findBrandByCategoryId (Integer categoryId);
}
