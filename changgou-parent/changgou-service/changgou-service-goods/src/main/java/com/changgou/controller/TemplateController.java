package com.changgou.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/template")
@CrossOrigin
@Api(value = "模板服务", description = "模板 api")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    @PostMapping(value = "/add")
    @ApiOperation("添加模板")
    public Result add (@RequestBody Template template) {
        templateService.add(template);
        return new Result();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("根据 id 删除模板")
    public Result delete (@PathVariable("id") Integer id) {
        templateService.delete(id);
        return new Result();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("根据 id 更新模板")
    public Result<Template> update (@RequestBody Template template, @PathVariable("id") Integer id) {
        template.setId(id);
        templateService.update(template);
        Template a = templateService.findById(id);
        return new Result<>(true, StatusCode.OK, "更新成功", template);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询单个模板")
    public Result<Template> findById (@PathVariable("id") Integer id) {
        Template template = templateService.findById(id);
        return new Result<>(true, StatusCode.OK, "查找成功", template);
    }

    @GetMapping
    @ApiOperation("查询所有")
    public Result<List<Template>> findAll () {
        return new Result<>(true, StatusCode.OK, "查找成功", templateService.findAll());
    }

    @PostMapping(value = "/search")
    @ApiOperation("多参数查询模板")
    public Result<List<Template>> findList (@RequestBody Template template) {
        return new Result<>(true, StatusCode.OK, "查找成功", templateService.findList(template));
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页查询")
    public Result<PageInfo<Template>> findPage (@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", templateService.findPage(pageNum, pageSize));
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页 + 多条件查询")
    public Result<PageInfo<Template>> findPage (@RequestBody Template template,
                                             @PathVariable("pageNum") int pageNum,
                                             @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", templateService.findPage(template, pageNum, pageSize));
    }
}
