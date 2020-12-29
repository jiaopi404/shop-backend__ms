package com.changgou.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.AlbumService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/album")
@CrossOrigin
@Api(value = "相册服务", description = "相册 api")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping(value = "/add")
    @ApiOperation("添加相册")
    public Result add (@RequestBody Album album) {
        albumService.add(album);
        return new Result();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("根据 id 删除相册")
    public Result delete (@PathVariable("id") Long id) {
        albumService.delete(id);
        return new Result();
    }

    @PutMapping(value = "/{id}")
    @ApiOperation("根据 id 更新相册")
    public Result<Album> update (@RequestBody Album album, @PathVariable("id") Long id) {
        album.setId(id);
        albumService.update(album);
        Album a = albumService.findById(id);
        return new Result<>(true, StatusCode.OK, "更新成功", album);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据 id 查询单个相册")
    public Result<Album> findById (@PathVariable("id") Long id) {
        Album album = albumService.findById(id);
        return new Result<>(true, StatusCode.OK, "查找成功", album);
    }

    @GetMapping
    @ApiOperation("查询所有")
    public Result<List<Album>> findAll () {
        return new Result<>(true, StatusCode.OK, "查找成功", albumService.findAll());
    }

    @PostMapping(value = "/search")
    @ApiOperation("多参数查询品牌")
    public Result<List<Album>> findList (@RequestBody Album album) {
        return new Result<>(true, StatusCode.OK, "查找成功", albumService.findList(album));
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页查询")
    public Result<PageInfo<Album>> findPage (@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", albumService.findPage(pageNum, pageSize));
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    @ApiOperation("分页 + 多条件查询")
    public Result<PageInfo<Album>> findPage (@RequestBody Album album,
                                             @PathVariable("pageNum") int pageNum,
                                             @PathVariable("pageSize") int pageSize) {
        return new Result<>(true, StatusCode.OK, "查找成功", albumService.findPage(album, pageNum, pageSize));
    }
}
