package com.changgou.service;

import com.changgou.goods.pojo.Para;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ParaService {

    /**
     * 根据 分类id 查询 参数集合；category -> template_id -> params
     * @param categoryId 分类 id
     * @return
     */
    List<Para> findParaByCategoryId (Integer categoryId);

    /**
     * add
     */
    void add (Para para);

    /**
     * delete
     */
    void delete(Integer id);

    /**
     * update
     */
    void update(Para para);

    /**
     * findAll
     */
    List<Para> findAll();

    /**
     * findById
     */
    Para findById(Integer id);

    /**
     * findList
     */
    List<Para> findList(Para para);

    /**
     * findPage
     */
    PageInfo<Para> findPage(Integer pageNum, Integer pageSize);

    /**
     * findPage 多条件查询
     */
    PageInfo<Para> findPage(Para para, Integer pageNum, Integer pageSize);
}
