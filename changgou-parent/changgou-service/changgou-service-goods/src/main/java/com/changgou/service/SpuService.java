package com.changgou.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:jiaopi404
 * @Description:Spu业务层接口
 * @Date 2021/1/4 14:30
 *****/
public interface SpuService {

    /**
     *
     * @param spuIds
     * @param isReal 是否物理删除；false 非 true 是
     */
    void deleteMany(Long[] spuIds, Boolean isReal);

    /**
     * 批量下架
     * @param spuIds
     */
    void pullMany(Long[] spuIds);

    /**
     * 批量上架
     * @param spuIds
     */
    void putMany(Long[] spuIds);

    /**
     * 上架
     * @param spuId
     */
    void put (Long spuId);

    /**
     * 下架
     * @param spuId
     */
    void pull (Long spuId);

    /**
     * 审核
     * @param spuId
     */
    void audit (Long spuId);

    /**
     * 根据 spuId 查询 goods
     * @param spuId spuId
     * @return
     */
    Goods findGoodsBySpuId (Long spuId);

    /**
     * 保存商品
     * @param goods
     */
    void saveGoods (Goods goods);

    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /***
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /***
     * Spu多条件搜索方法
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /***
     * 删除Spu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Spu数据
     * @param spu
     */
    void update(Spu spu);

    /***
     * 新增Spu
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据ID查询Spu
     * @param id
     * @return
     */
     Spu findById(Long id);

    /***
     * 查询所有Spu
     * @return
     */
    List<Spu> findAll();
}
