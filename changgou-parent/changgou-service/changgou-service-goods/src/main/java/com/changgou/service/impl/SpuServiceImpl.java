package com.changgou.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.dao.BrandMapper;
import com.changgou.dao.CategoryMapper;
import com.changgou.dao.SkuMapper;
import com.changgou.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/****
 * @Author:jiaopi404
 * @Description:Spu业务层接口实现类
 * @Date 2021/1/4 14:30
 *****/
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    // 注入 idWorker
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 删除，区分是否物理删除
     * @param spuIds spu ids
     * @param isReal 是否物理删除；false 非 true 是
     */
    @Override
    public void deleteMany(Long[] spuIds, Boolean isReal) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(spuIds));
        if (isReal) { // 物理删除
            spuMapper.deleteByExample(example);
        } else {
            Spu spu = new Spu();
            spu.setIsDelete("1");
            spuMapper.updateByExampleSelective(spu, example);
        }
    }

    /**
     * 批量下架
     * @param spuIds spu ids
     */
    @Override
    public void pullMany(Long[] spuIds) {
        // sql
        // update tb_spu set isMarketable=1 where id in(spuIds) and is_delete=0 and status=1
        // 构建条件 Example
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // id in spuIds
        criteria.andIn("id", Arrays.asList(spuIds)); // 数组转为 集合；
        // isDelete = 0
        criteria.andEqualTo("isDelete", "0");
        Spu spu = new Spu();
        spu.setIsMarketable("0");
        int i = spuMapper.updateByExampleSelective(spu, example); // i 操作的行数（无论值是否已经是期望值）
        if (i == 0) {
            throw new RuntimeException("并没有查询到要修改的行，请确认检索条件！");
        }
    }

    /**
     * 批量上架
     * @param spuIds spu ids
     */
    @Override
    public void putMany(Long[] spuIds) {
        // sql
        // update tb_spu set isMarketable=1 where id in(spuIds) and is_delete=0 and status=1
        // 构建条件 Example
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // id in spuIds
        criteria.andIn("id", Arrays.asList(spuIds)); // 数组转为 集合；
        // isDelete = 0
        criteria.andEqualTo("isDelete", "0");
        // status = 1
        criteria.andEqualTo("status", "1");
        Spu spu = new Spu();
        spu.setIsMarketable("1");
        int i = spuMapper.updateByExampleSelective(spu, example); // i 操作的行数（无论值是否已经是期望值）
        if (i == 0) {
            throw new RuntimeException("并没有查询到要修改的行，请确认检索条件！");
        }
    }

    /**
     * 上架
     * @param spuId
     */
    @Override
    public void put(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断是否已删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) { // 1 已删除
            throw new RuntimeException("商品已删除，不允许上架");
        }
        if (spu.getStatus().equalsIgnoreCase("0")) { // 1 已删除
            throw new RuntimeException("商品未审核，不允许上架");
        }
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * 下架
     * @param spuId
     */
    @Override
    public void pull(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断是否已删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) { // 1 已删除
            throw new RuntimeException("商品已删除，不允许下架");
        }
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 审核
     * @param spuId
     */
    @Override
    public void audit(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        // 判断是否已删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) { // 1 已删除
            throw new RuntimeException("商品已删除，不允许审核");
        }
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 根据 spuId 查询 goods
     * @param spuId spuId
     * @return
     */
    @Override
    public Goods findGoodsBySpuId(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = skuMapper.select(sku);
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /**
     * 保存商品
     * @param goods
     */
    @Override
    public void saveGoods(Goods goods) {
        // 1. spu
        Spu spu = goods.getSpu();
        // 区分 添加 和 修改
        if (spu.getId() == null) { // 添加
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else { // 修改
            spuMapper.updateByPrimaryKeySelective(spu);
            // 清空 skuList
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }
        // 1.5 sku 的前置处理
        Date date = new Date();
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        // 2. skuList
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {
            sku.setId(idWorker.nextId()); // id
            // 处理名称
            StringBuilder name = new StringBuilder(spu.getName());
            // 防止空指针
            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}"); // 设置默认值
            }
            Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                name.append(" ").append(entry.getValue());
            }

            sku.setName(name.toString()); // spu.name + 规格信息;
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setSpuId(spu.getId());
            sku.setCategoryId(spu.getCategory3Id());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
    }

    /**
     * Spu条件+分页查询
     * @param spu 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu){
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     * @param spu
     * @return
     */
    public Example createExample(Spu spu){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(spu!=null){
            // 主键
            if(!StringUtils.isEmpty(spu.getId())){
                    criteria.andEqualTo("id",spu.getId());
            }
            // 货号
            if(!StringUtils.isEmpty(spu.getSn())){
                    criteria.andEqualTo("sn",spu.getSn());
            }
            // SPU名
            if(!StringUtils.isEmpty(spu.getName())){
                    criteria.andLike("name","%"+spu.getName()+"%");
            }
            // 副标题
            if(!StringUtils.isEmpty(spu.getCaption())){
                    criteria.andEqualTo("caption",spu.getCaption());
            }
            // 品牌ID
            if(!StringUtils.isEmpty(spu.getBrandId())){
                    criteria.andEqualTo("brandId",spu.getBrandId());
            }
            // 一级分类
            if(!StringUtils.isEmpty(spu.getCategory1Id())){
                    criteria.andEqualTo("category1Id",spu.getCategory1Id());
            }
            // 二级分类
            if(!StringUtils.isEmpty(spu.getCategory2Id())){
                    criteria.andEqualTo("category2Id",spu.getCategory2Id());
            }
            // 三级分类
            if(!StringUtils.isEmpty(spu.getCategory3Id())){
                    criteria.andEqualTo("category3Id",spu.getCategory3Id());
            }
            // 模板ID
            if(!StringUtils.isEmpty(spu.getTemplateId())){
                    criteria.andEqualTo("templateId",spu.getTemplateId());
            }
            // 运费模板id
            if(!StringUtils.isEmpty(spu.getFreightId())){
                    criteria.andEqualTo("freightId",spu.getFreightId());
            }
            // 图片
            if(!StringUtils.isEmpty(spu.getImage())){
                    criteria.andEqualTo("image",spu.getImage());
            }
            // 图片列表
            if(!StringUtils.isEmpty(spu.getImages())){
                    criteria.andEqualTo("images",spu.getImages());
            }
            // 售后服务
            if(!StringUtils.isEmpty(spu.getSaleService())){
                    criteria.andEqualTo("saleService",spu.getSaleService());
            }
            // 介绍
            if(!StringUtils.isEmpty(spu.getIntroduction())){
                    criteria.andEqualTo("introduction",spu.getIntroduction());
            }
            // 规格列表
            if(!StringUtils.isEmpty(spu.getSpecItems())){
                    criteria.andEqualTo("specItems",spu.getSpecItems());
            }
            // 参数列表
            if(!StringUtils.isEmpty(spu.getParaItems())){
                    criteria.andEqualTo("paraItems",spu.getParaItems());
            }
            // 销量
            if(!StringUtils.isEmpty(spu.getSaleNum())){
                    criteria.andEqualTo("saleNum",spu.getSaleNum());
            }
            // 评论数
            if(!StringUtils.isEmpty(spu.getCommentNum())){
                    criteria.andEqualTo("commentNum",spu.getCommentNum());
            }
            // 是否上架,0已下架，1已上架
            if(!StringUtils.isEmpty(spu.getIsMarketable())){
                    criteria.andEqualTo("isMarketable",spu.getIsMarketable());
            }
            // 是否启用规格
            if(!StringUtils.isEmpty(spu.getIsEnableSpec())){
                    criteria.andEqualTo("isEnableSpec",spu.getIsEnableSpec());
            }
            // 是否删除,0:未删除，1：已删除
            if(!StringUtils.isEmpty(spu.getIsDelete())){
                    criteria.andEqualTo("isDelete",spu.getIsDelete());
            }
            // 审核状态，0：未审核，1：已审核，2：审核不通过
            if(!StringUtils.isEmpty(spu.getStatus())){
                    criteria.andEqualTo("status",spu.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     * @param spu
     */
    @Override
    public void update(Spu spu){
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     * @param spu
     */
    @Override
    public void add(Spu spu){
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id){
        return  spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }
}
