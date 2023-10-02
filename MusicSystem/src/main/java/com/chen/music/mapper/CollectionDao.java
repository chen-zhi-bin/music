package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.music.pojo.Collection;
import com.chen.music.pojo.Comment;
import com.chen.music.pojo.vo.CollectionVo;
import com.chen.music.pojo.vo.MusicAndSingerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface CollectionDao extends BaseMapper<Collection> {


    @Select("SELECT       \n" +
            "\t\n" +
            "\ttb_collection.`music_id`,\n" +
            "\ttb_music.`name`,\n" +
            "\ttb_music.`singer_name`,\n" +
            "\ttb_music.`url`,\n" +
            "\ttb_music.`file_high_url`,\n" +
            "\ttb_music.`duration`,\n" +
            "\ttb_music.`pic_id`\n" +
            "  FROM tb_collection JOIN tb_music ON tb_collection.`music_id` = tb_music.`id` " +
            "${ew.customSqlSegment}")
    IPage<CollectionVo> getMusicListByPage(IPage<CollectionVo> page,
                                               @Param("ew") Wrapper wrapper);

}
