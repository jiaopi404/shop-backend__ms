package com.changgou.dao;

import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/**
 * DAO 使用通用 Mapper, Dao 接口需要继承  tk.mybatis.mapper.common.Mapper
 *   增加数据: 调用 Mapper.insert(), or Mapper.insertSelective()
 *
 *   修改: Mapper.update(T), or Mapper.updateByPrimaryKey(T)
 *
 *   查询: Mapper.selectByPrimaryKey(id)
 *   条件查询: Mapper.select(T) // T JavaBean 对象
 */
public interface BrandMapper extends Mapper<Brand> {
}
