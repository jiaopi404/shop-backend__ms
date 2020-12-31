package com.changgou.service.impl;

import com.changgou.dao.ParaMapper;
import com.changgou.dao.TemplateMapper;
import com.changgou.goods.pojo.Para;
import com.changgou.goods.pojo.Template;
import com.changgou.service.ParaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {
    @Autowired
    private ParaMapper paraMapper;

    @Autowired
    private TemplateMapper templateMapper;
    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Para> findAll() {
        // 查询所有 -> 通用Mapper.selectAll()
        return paraMapper.selectAll(); // ParaMapper 继承了通用 Mapper
    }

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加参数
     * @param para
     * @return 创建的 id
     */
    @Override
    public void add(Para para) {
        paraMapper.insert(para);
        updateParaNum(para, 1); // 更改 模板 表中 参数 数量；
    }

    /**
     * 更新参数
     * @param para
     */
    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKey(para);
    }

    /**
     * 删除 参数
     * @param paraId Integer Para.id
     */
    @Override
    public void delete(Integer paraId) {
        Para para = paraMapper.selectByPrimaryKey(paraId);
        paraMapper.deleteByPrimaryKey(paraId);
        updateParaNum(para, -1); // 更改 模板 表中 参数 数量；
    }

    /**
     * 多条件搜索
     * @param para
     * @return
     */
    @Override
    public List<Para> findList(Para para) {
        // Example 自定义条件搜索对象
        Example example = createExample(para);
        return paraMapper.selectByExample(example);
    }

    /**
     * 分页查询
     * @param pageNum 分页
     * @param pageSize 分页
     * @return
     */
    @Override
    public PageInfo<Para> findPage(Integer pageNum, Integer pageSize) {
        /**
         * 分页实现; 后面的查询紧跟集合查询
         * 1: 当前页 2. 每页显示多少条
         */
        PageHelper.startPage(pageNum, pageSize);

        List<Para> paraList = paraMapper.selectAll();
        return new PageInfo<>(paraList);
    }

    /**
     * 条件 + 分页 查询
     * @param para
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Para> findPage(Para para, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = createExample(para);
        List<Para> paraListByExample = paraMapper.selectByExample(example);
        return new PageInfo<>(paraListByExample);
    }

    public Example createExample (Para para) {
        Example example = new Example(Para.class); // Para.class 当前操作对象的字节码
        Example.Criteria criteria = example.createCriteria(); // 条件构造器

        if (para != null) {
            // 参数名称 根据名字模糊搜索;
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name", "%" + para.getName() + "%");
            }
            // 参数名称 根据参数选项模糊搜索;
            if (!StringUtils.isEmpty(para.getOptions())) {
                criteria.andLike("options", "%" + para.getOptions() + "%");
            }
            // 参数的 顺序
            if (!StringUtils.isEmpty(para.getSeq())) {
                criteria.andEqualTo("seq", para.getSeq());
            }
            // 参数id
            if(!StringUtils.isEmpty(para.getId())){
                criteria.andEqualTo("id", para.getId());
            }
            // 参数的 模板id
            if(!StringUtils.isEmpty(para.getTemplateId())){
                criteria.andEqualTo("templateId", para.getTemplateId());
            }
        }

        return example;
    }

    public void updateParaNum (Para para, Integer count) {
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        Integer paraNum = template.getParaNum();
        if (paraNum == null) {
            paraNum = 0;
        }
        template.setParaNum(paraNum + count);
        templateMapper.updateByPrimaryKeySelective(template);
    }
}
