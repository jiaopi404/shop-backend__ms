package com.changgou.service;

import com.changgou.goods.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    /**
     * add
     */
    void add (Category category);

    /**
     * delete
     */
    void delete(Integer id);

    /**
     * update
     */
    void update(Category category);

    /**
     * findAll
     */
    List<Category> findAll();

    /**
     * findById
     */
    Category findById(Integer id);

    /**
     * findList
     */
    List<Category> findList(Category category);

    /**
     * findPage
     */
    PageInfo<Category> findPage(Integer pageNum, Integer pageSize);

    /**
     * findPage 多条件查询
     */
    PageInfo<Category> findPage(Category category, Integer pageNum, Integer pageSize);

    /**
     * findByParentId 根据目录父 id 查找目录(上级id)
     * @param parentId 父节点 id; 一级分类为 0
     * @return
     */
    List<Category> findByParentId (Integer parentId);
}
