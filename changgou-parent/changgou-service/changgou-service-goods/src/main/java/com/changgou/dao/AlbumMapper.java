package com.changgou.dao;

import com.changgou.goods.pojo.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 通用 Mapper
 */
@Repository
public interface AlbumMapper extends Mapper<Album> {
}
