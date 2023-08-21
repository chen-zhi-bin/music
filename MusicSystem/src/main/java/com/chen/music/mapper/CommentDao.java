package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.music.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.music.pojo.vo.CommentAndMusicVo;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface CommentDao extends BaseMapper<Comment> {

    @Select("SELECT \n" +
            "\ttb_comment.`id`,\n" +
            "\ttb_comment.`avatar`,\n" +
            "\ttb_comment.`user_id`,\n" +
            "\ttb_comment.`user_name`,\n" +
            "\ttb_comment.`song_id`,\n" +
            "\ttb_comment.`comment`,\n" +
            "\ttb_comment.`create_time` AS comment_time,\n" +
            "\ttb_music.`id` AS music_id,\n" +
            "\ttb_music.`singer_name`,\n" +
            "\ttb_music.`singer_id`,\n" +
            "\ttb_music.`name` AS music_name,\n" +
            "\ttb_music.`pic_id`,\n" +
            "\ttb_music.`url`,\n" +
            "\ttb_music.`duration`,\n" +
            "\ttb_music.`content_type`,\n" +
            "\ttb_music.`file_high_url`\n" +
            "FROM tb_comment\n" +
            "\tLEFT JOIN tb_music ON tb_comment.`song_id` = tb_music.`id`" +
            "${ew.customSqlSegment}")
    IPage<CommentAndMusicVo> getCommentList(IPage<CommentAndMusicVo> page,
                                                @Param("ew") Wrapper wrapper);

}
