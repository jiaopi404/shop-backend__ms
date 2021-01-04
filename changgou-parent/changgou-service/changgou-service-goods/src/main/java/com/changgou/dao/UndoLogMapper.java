package com.changgou.dao;
import com.changgou.goods.pojo.UndoLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:jiaopi404
 * @Description:UndoLog的Dao
 * @Date 2021/1/4 14:30
 *****/
@Repository
public interface UndoLogMapper extends Mapper<UndoLog> {
}
