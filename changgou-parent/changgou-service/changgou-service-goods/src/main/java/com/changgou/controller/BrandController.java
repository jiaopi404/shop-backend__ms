package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin // 跨域(域名 + 端口 + 协议)
@Api(value = "品牌api", description = "品牌 api")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @ApiOperation("查询所有品牌")
    public Result<List<Brand>> findAll() {
        // 查询所有品牌
        List<Brand> brandList = brandService.findAll();
        // 响应结果封装
        return new Result<>(true, StatusCode.OK, "查询品牌集合成功", brandList);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询品牌")
    public Result<Brand> findById (@PathVariable("id") Integer id) {
        Brand b = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询单个品牌成功", b);
    }

    @PostMapping(value = "/add")
    @ApiOperation("添加品牌")
    public Result<Integer> add (@RequestBody Brand brand) {
        int i = brandService.add(brand);
        return new Result<>(true, StatusCode.OK, "添加成功", i);
    }

    @ApiOperation("添加品牌 as form")
    @PostMapping(value = "/add-form")
    public Result<Integer> add (@RequestParam String name, String image, String letter, Integer seq) {
        Brand brand = new Brand();
        brand.setImage(image);
        brand.setName(name);
        brand.setLetter(letter);
        brand.setSeq(seq);
        int i = brandService.add(brand);
        return new Result<>(true, StatusCode.OK, "添加成功 as form", i);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("更新品牌")
    /**
     * 更新，理论上应该是差量更新：1. 获取参数or请求体 2. findById 3. 请求参数判断是否存在
     */
    public Result<Brand> update (@RequestBody Brand brand, @PathVariable("id") Integer brandId) {
        brand.setId(brandId);
        brandService.update(brand);
        Brand b = brandService.findById(brandId);
        return new Result<>(true, StatusCode.OK, "更新成功", b);
    }

    /**
     * 删除品牌
     * @param brandId
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete (@PathVariable("id") Integer brandId) {
        brandService.delete(brandId);
        return new Result();
    }
}
