package com.changgou.service;

import com.changgou.goods.pojo.Template;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TemplateService {
    // 不需要写 DAO, 直接写 service

    /**
     * 查询所有
     * @return
     */
    List<Template> findAll();

    /**
     * 根据 id 获取 template
     * @param id
     * @return
     */
    Template findById(Integer id);

    /**
     * 增
     * @param template Template
     */
    int add(Template template);

    /**
     * 改
     * @param template
     */
    void update(Template template);

    /**
     * 删
     * @param templateId Integer Template.id
     */
    void delete(Integer templateId);

    /**
     * 多条件搜索模板
     * @param template
     * @return
     */
    List<Template> findList (Template template);

    /**
     * 分页
     * @param pageNum 分页
     * @param pageSize 分页
     */
    PageInfo<Template> findPage (Integer pageNum, Integer pageSize);

    /**
     * 条件 + 分页 查询
     */
    PageInfo<Template> findPage (Template template, Integer pageNum, Integer pageSize);
}
