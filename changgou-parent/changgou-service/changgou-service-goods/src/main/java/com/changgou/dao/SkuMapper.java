package com.changgou.dao;
import com.changgou.goods.pojo.Sku;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:jiaopi404
 * @Description:Sku的Dao
 * @Date 2021/1/4 14:30
 *****/
@Repository
public interface SkuMapper extends Mapper<Sku> {
}
