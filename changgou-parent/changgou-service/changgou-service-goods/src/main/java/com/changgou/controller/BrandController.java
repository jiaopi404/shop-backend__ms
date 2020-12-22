package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin // 跨域(域名 + 端口 + 协议)
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAll() {
        // 查询所有品牌
        List<Brand> brandList = brandService.findAll();
        // 响应结果封装
        return new Result<>(true, StatusCode.OK, "查询品牌集合成功", brandList);
    }

    @GetMapping(value = "/{id}")
    public Result<Brand> findOne (@PathVariable("id") Integer id) {
        Brand b = brandService.findOneById(id);
        return new Result<>(true, StatusCode.OK, "查询单个品牌成功", b);
    }
}
