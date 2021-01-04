package com.changgou.service.impl;

import com.changgou.dao.CategoryMapper;
import com.changgou.dao.SpecMapper;
import com.changgou.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Spec;
import com.changgou.goods.pojo.Template;
import com.changgou.service.SpecService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Spec> findAll() {
        // 查询所有 -> 通用Mapper.selectAll()
        return specMapper.selectAll(); // SpecMapper 继承了通用 Mapper
    }

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据 分类id 查询 规格集合；category -> template_id -> specs
     * @param categoryId 分类id
     * @return
     */
    @Override
    public List<Spec> findSpecByCategoryId(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        Spec spec = new Spec();
        spec.setTemplateId(category.getTemplateId());
        return specMapper.select(spec);
    }

    /**
     * 添加规格
     * @param spec
     * @return 创建的 id
     */
    @Override
    public void add(Spec spec) {
        specMapper.insert(spec);
        updateSpecNum(spec, 1); // 更改 模板 表中 规格 数量；
    }

    /**
     * 更新规格
     * @param spec
     */
    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    /**
     * 删除 规格
     * @param specId Integer Spec.id
     */
    @Override
    public void delete(Integer specId) {
        Spec spec = specMapper.selectByPrimaryKey(specId);
        specMapper.deleteByPrimaryKey(specId);
        updateSpecNum(spec, -1); // 更改 模板 表中 规格 数量；
    }

    /**
     * 多条件搜索
     * @param spec
     * @return
     */
    @Override
    public List<Spec> findList(Spec spec) {
        // Example 自定义条件搜索对象
        Example example = createExample(spec);
        return specMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param pageNum 分页
     * @param pageSize 分页
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(Integer pageNum, Integer pageSize) {
        /**
         * 分页实现; 后面的查询紧跟集合查询
         * 1: 当前页 2. 每页显示多少条
         */
        PageHelper.startPage(pageNum, pageSize);

        List<Spec> specList = specMapper.selectAll();
        return new PageInfo<>(specList);
    }

    /**
     * 条件 + 分页 查询
     * @param spec
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Spec> findPage(Spec spec, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = createExample(spec);
        List<Spec> specListByExample = specMapper.selectByExample(example);
        return new PageInfo<>(specListByExample);
    }

    public Example createExample (Spec spec) {
        Example example = new Example(Spec.class); // Spec.class 当前操作对象的字节码
        Example.Criteria criteria = example.createCriteria(); // 条件构造器

        if (spec != null) {
            // 规格名称 根据名字模糊搜索;
            if (!StringUtils.isEmpty(spec.getName())) {
                criteria.andLike("name", "%" + spec.getName() + "%");
            }
            // 规格名称 根据规格选项模糊搜索;
            if (!StringUtils.isEmpty(spec.getOptions())) {
                criteria.andLike("options", "%" + spec.getOptions() + "%");
            }
            // 规格的 顺序
            if (!StringUtils.isEmpty(spec.getSeq())) {
                criteria.andEqualTo("seq", spec.getSeq());
            }
            // 规格id
            if(!StringUtils.isEmpty(spec.getId())){
                criteria.andEqualTo("id", spec.getId());
            }
            // 规格的 模板 id
            if(!StringUtils.isEmpty(spec.getTemplateId())){
                criteria.andEqualTo("templateId", spec.getTemplateId());
            }
        }

        return example;
    }

    public void updateSpecNum (Spec spec, Integer count) {
        Template template = templateMapper.selectByPrimaryKey(spec.getTemplateId());
        Integer specNum = template.getSpecNum();
        if (specNum == null) {
            specNum = 0;
        }
        template.setSpecNum(specNum + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }
}
