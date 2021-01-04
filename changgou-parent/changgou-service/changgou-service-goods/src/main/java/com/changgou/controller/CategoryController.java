package com.changgou.controller;

import com.changgou.goods.pojo.Category;
import com.changgou.service.CategoryService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
@Api(value = "目录服务", description = "目录 api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/add")
    @ApiOperation("添加目录")
    public Result add (@RequestBody Category category) {
        categoryService.add(category);
        return new Result();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("根据 id 删除目录")
    public Result delete (@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return new Result();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("根据 id 更新目录")
    public Result<Category> update (@RequestBody Category category, @PathVariable("id") Integer id) {
        category.setId(id);
        categoryService.update(category);
        Category a = categoryService.findById(id);
        return new Result<>(true, StatusCode.OK, "更新成功", a);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询单个目录")
    public Result<Category> findById (@PathVariable("id") Integer id) {
        Category category = categoryService.findById(id);
        return new Result<>(true, StatusCode.OK, "查找成功", category);
    }

    @GetMapping
    @ApiOperation("查询所有")
    public Result<List<Category>> findAll () {
        return new Result<>(true, StatusCode.OK, "查找成功", categoryService.findAll());
    }

    @PostMapping(value = "/search")
    @ApiOperation("多参数查询目录")
    public Result<List<Category>> findList (@RequestBody Category category) {
        return new Result<>(true, StatusCode.OK, "查找成功", categoryService.findList(category));
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页查询")
    public Result<PageInfo<Category>> findPage (@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", categoryService.findPage(pageNum, pageSize));
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页 + 多条件查询")
    public Result<PageInfo<Category>> findPage (@RequestBody Category category,
                                            @PathVariable("pageNum") int pageNum,
                                            @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", categoryService.findPage(category, pageNum, pageSize));
    }

    @GetMapping(value = "/list/{parentId}")
    @ApiOperation("根据 父id 查询 子节点")
    public Result<List<Category>> findByParentId (@PathVariable("parentId") Integer parentId) {
        return new Result<>(true, StatusCode.OK, "根据父节点查询子节点成功", categoryService.findByParentId(parentId));
    }
}
