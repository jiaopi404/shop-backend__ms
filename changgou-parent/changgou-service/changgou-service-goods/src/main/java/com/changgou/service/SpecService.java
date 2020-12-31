package com.changgou.service;

import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SpecService {
    /**
     * add
     */
    void add (Spec spec);

    /**
     * delete
     */
    void delete(Integer id);

    /**
     * update
     */
    void update(Spec spec);

    /**
     * findAll
     */
    List<Spec> findAll();

    /**
     * findById
     */
    Spec findById(Integer id);

    /**
     * findList
     */
    List<Spec> findList(Spec spec);

    /**
     * findPage
     */
    PageInfo<Spec> findPage(Integer pageNum, Integer pageSize);

    /**
     * findPage 多条件查询
     */
    PageInfo<Spec> findPage(Spec spec, Integer pageNum, Integer pageSize);
}
