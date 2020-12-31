package com.changgou.service.impl;

import com.changgou.dao.CategoryMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {
        // 查询所有 -> 通用Mapper.selectAll()
        return categoryMapper.selectAll(); // CategoryMapper 继承了通用 Mapper
    }

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加分类
     * @param category
     * @return 创建的 id
     */
    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * 更新分类
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除 分类
     * @param categoryId Integer Category.id
     */
    @Override
    public void delete(Integer categoryId) {
        categoryMapper.deleteByPrimaryKey(categoryId);
    }

    /**
     * 多条件搜索
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        // Example 自定义条件搜索对象
        Example example = createExample(category);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param pageNum 分页
     * @param pageSize 分页
     * @return
     */
    @Override
    public PageInfo<Category> findPage(Integer pageNum, Integer pageSize) {
        /**
         * 分页实现; 后面的查询紧跟集合查询
         * 1: 当前页 2. 每页显示多少条
         */
        PageHelper.startPage(pageNum, pageSize);

        List<Category> categoryList = categoryMapper.selectAll();
        return new PageInfo<>(categoryList);
    }

    /**
     * 条件 + 分页 查询
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Category> findPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = createExample(category);
        List<Category> categoryListByExample = categoryMapper.selectByExample(example);
        return new PageInfo<>(categoryListByExample);
    }

    @Override
    public List<Category> findByParentId(Integer parentId) {
        Category category = new Category();
        category.setParentId(parentId);
        return categoryMapper.select(category);
    }

    public Example createExample (Category category) {
        Example example = new Example(Category.class); // Category.class 当前操作对象的字节码
        Example.Criteria criteria = example.createCriteria(); // 条件构造器

        if (category != null) {
            // 分类名称 模糊搜索
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name", "%" + category.getName() + "%");
            }
            // 分类名称 根据分类选项模糊搜索;
            if (!StringUtils.isEmpty(category.getGoodsNum())) {
                criteria.andEqualTo("goodsNum", category.getGoodsNum());
            }
            // 分类 是否显示
            if (!StringUtils.isEmpty(category.getIsShow())) {
                criteria.andEqualTo("isShow", category.getIsShow());
            }
            // 分类 是否导航
            if (!StringUtils.isEmpty(category.getIsMenu())) {
                criteria.andEqualTo("isMenu", category.getIsMenu());
            }
            // 分类的 顺序
            if (!StringUtils.isEmpty(category.getSeq())) {
                criteria.andEqualTo("seq", category.getSeq());
            }
            // 分类id
            if(!StringUtils.isEmpty(category.getId())){
                criteria.andEqualTo("id", category.getId());
            }
            // 分类 上级 ID
            if(!StringUtils.isEmpty(category.getParentId())){
                criteria.andEqualTo("parentId", category.getParentId());
            }
            // 分类的 模板id
            if(!StringUtils.isEmpty(category.getTemplateId())){
                criteria.andEqualTo("templateId", category.getTemplateId());
            }
        }

        return example;
    }
}
