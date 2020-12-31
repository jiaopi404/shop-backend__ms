package com.changgou.service.impl;

import com.changgou.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;
    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Template> findAll() {
        // 查询所有 -> 通用Mapper.selectAll()
        return templateMapper.selectAll(); // TemplateMapper 继承了通用 Mapper
    }

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加模板
     * @param template
     * @return 创建的 id
     */
    @Override
    public int add(Template template) {
        return templateMapper.insert(template);
    }

    /**
     * 更新模板
     * @param template
     */
    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    /**
     * 删除 模板
     * @param templateId Integer Template.id
     */
    @Override
    public void delete(Integer templateId) {
        templateMapper.deleteByPrimaryKey(templateId);
    }

    /**
     * 多条件搜索
     * @param template
     * @return
     */
    @Override
    public List<Template> findList(Template template) {
        // Example 自定义条件搜索对象
        Example example = createExample(template);
        return templateMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param pageNum 分页
     * @param pageSize 分页
     * @return
     */
    @Override
    public PageInfo<Template> findPage(Integer pageNum, Integer pageSize) {
        /**
         * 分页实现; 后面的查询紧跟集合查询
         * 1: 当前页 2. 每页显示多少条
         */
        PageHelper.startPage(pageNum, pageSize);

        List<Template> templateList = templateMapper.selectAll();
        return new PageInfo<>(templateList);
    }

    /**
     * 条件 + 分页 查询
     * @param template
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Template> findPage(Template template, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = createExample(template);
        List<Template> templateListByExample = templateMapper.selectByExample(example);
        return new PageInfo<>(templateListByExample);
    }

    public Example createExample (Template template) {
        Example example = new Example(Template.class); // Template.class 当前操作对象的字节码
        Example.Criteria criteria = example.createCriteria(); // 条件构造器

        if (template != null) {
            // 模板名称 根据名字模糊搜索;
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            // 模板的 参数数量
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum", template.getParaNum());
            }
            // 模板id
            if(!StringUtils.isEmpty(template.getId())){
                criteria.andEqualTo("id", template.getId());
            }
            // 模板的 规格数量
            if(!StringUtils.isEmpty(template.getSpecNum())){
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
        }

        return example;
    }
}
