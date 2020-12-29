package com.changgou.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AlbumService {
    /**
     * add
     */
    void add (Album album);

    /**
     * delete
     */
    void delete(Long id);

    /**
     * update
     */
    void update(Album album);

    /**
     * findAll
     */
    List<Album> findAll();

    /**
     * findById
     */
    Album findById(Long id);

    /**
     * findList
     */
    List<Album> findList(Album album);

    /**
     * findPage
     */
    PageInfo<Album> findPage(int pageNum, int pageSize);

    /**
     * findPage 多条件查询
     */
    PageInfo<Album> findPage(Album album, int pageNum, int pageSize);
}
