package com.changgou.controller;

import com.changgou.goods.pojo.Para;
import com.changgou.service.ParaService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/para")
@CrossOrigin
@Api(value = "参数服务", description = "参数 api")
public class ParaController {
    @Autowired
    private ParaService paraService;

    /**
     * 根据 分类id 查询 参数集合；category -> template_id -> params
     */
    @GetMapping("/category/{categoryId}")
    @ApiOperation("根据 分类id 查询 参数集合；category -> template_id -> params")
    public Result<List<Para>> findParaByCategoryId (@PathVariable("categoryId") Integer categoryId) {
        return new Result<>(
                true,
                StatusCode.OK,
                "根据 分类id 查询 参数集合；category -> template_id -> params 成功！",
                paraService.findParaByCategoryId(categoryId)
        );
    }

    @PostMapping(value = "/add")
    @ApiOperation("添加参数")
    public Result add (@RequestBody Para para) {
        paraService.add(para);
        return new Result();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("根据 id 删除参数")
    public Result delete (@PathVariable("id") Integer id) {
        paraService.delete(id);
        return new Result();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("根据 id 更新参数")
    public Result<Para> update (@RequestBody Para para, @PathVariable("id") Integer id) {
        para.setId(id);
        paraService.update(para);
        Para a = paraService.findById(id);
        return new Result<>(true, StatusCode.OK, "更新成功", para);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询单个参数")
    public Result<Para> findById (@PathVariable("id") Integer id) {
        Para para = paraService.findById(id);
        return new Result<>(true, StatusCode.OK, "查找成功", para);
    }

    @GetMapping
    @ApiOperation("查询所有")
    public Result<List<Para>> findAll () {
        return new Result<>(true, StatusCode.OK, "查找成功", paraService.findAll());
    }

    @PostMapping(value = "/search")
    @ApiOperation("多参数查询参数")
    public Result<List<Para>> findList (@RequestBody Para para) {
        return new Result<>(true, StatusCode.OK, "查找成功", paraService.findList(para));
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页查询")
    public Result<PageInfo<Para>> findPage (@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", paraService.findPage(pageNum, pageSize));
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页 + 多条件查询")
    public Result<PageInfo<Para>> findPage (@RequestBody Para para,
                                            @PathVariable("pageNum") int pageNum,
                                            @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", paraService.findPage(para, pageNum, pageSize));
    }
}
