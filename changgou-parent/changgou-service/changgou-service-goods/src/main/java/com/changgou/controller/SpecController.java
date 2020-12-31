package com.changgou.controller;

import com.changgou.goods.pojo.Spec;
import com.changgou.service.SpecService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/spec")
@CrossOrigin
@Api(value = "规格服务", description = "规格 api")
public class SpecController {
    @Autowired
    private SpecService specService;

    @PostMapping(value = "/add")
    @ApiOperation("添加规格")
    public Result add (@RequestBody Spec spec) {
        specService.add(spec);
        return new Result();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("根据 id 删除规格")
    public Result delete (@PathVariable("id") Integer id) {
        specService.delete(id);
        return new Result();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("根据 id 更新规格")
    public Result<Spec> update (@RequestBody Spec spec, @PathVariable("id") Integer id) {
        spec.setId(id);
        specService.update(spec);
        Spec a = specService.findById(id);
        return new Result<>(true, StatusCode.OK, "更新成功", spec);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询单个规格")
    public Result<Spec> findById (@PathVariable("id") Integer id) {
        Spec spec = specService.findById(id);
        return new Result<>(true, StatusCode.OK, "查找成功", spec);
    }

    @GetMapping
    @ApiOperation("查询所有")
    public Result<List<Spec>> findAll () {
        return new Result<>(true, StatusCode.OK, "查找成功", specService.findAll());
    }

    @PostMapping(value = "/search")
    @ApiOperation("多参数查询规格")
    public Result<List<Spec>> findList (@RequestBody Spec spec) {
        return new Result<>(true, StatusCode.OK, "查找成功", specService.findList(spec));
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页查询")
    public Result<PageInfo<Spec>> findPage (@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", specService.findPage(pageNum, pageSize));
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页 + 多条件查询")
    public Result<PageInfo<Spec>> findPage (@RequestBody Spec spec,
                                                @PathVariable("pageNum") int pageNum,
                                                @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", specService.findPage(spec, pageNum, pageSize));
    }
}
