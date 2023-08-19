package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.music.pojo.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.music.pojo.vo.MusicAndSingerVo;
import com.chen.music.pojo.vo.MusicItem;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface MusicDao extends BaseMapper<Music> {

    @Update("UPDATE `tb_music` set `state`=0 where `id` = #{id}")
    int deleteByMusicId(@Param("id")String id);

//    @Select("SELECT tb_music.id,\n" +
//            "\ttb_music.`name` AS music_name,\n" +
//            "\ttb_music.`url`,\n" +
//            "\ttb_music.`file_high_url`,\n" +
//            "\ttb_singer.`name` AS singr_name  FROM tb_music LEFT JOIN tb_singer ON tb_singer.`id` = tb_music.`singer_id`\n" +
//            "${ew.customSqlSegment}")
//    IPage<MusicAndSingerVo> getMusicListByPage(IPage<MusicAndSingerVo> page,
//                                               @Param("ew") Wrapper wrapper);

    //admin使用
    @Select("SELECT tb_music.id AS music_id,\n" +
            "           tb_music.`name` AS music_name,\n" +
            "           tb_music.`url`,\n" +
            "           tb_music.`file_high_url`,\n" +
            "           tb_music.`create_time`,\n" +
            "           tb_music.`update_time`,\n" +
            "           tb_music.`singer_name`,\n" +
            "           tb_user.`id` AS user_id,\n" +
            "           tb_user.`user_name`,\n" +
            "           tb_music.`state`,\n" +
            "           tb_singer.`name` AS singer_name  FROM tb_music LEFT JOIN tb_singer ON tb_singer.`id` = tb_music.`singer_id`\n" +
            "\t\tJOIN tb_user ON tb_music.`user_id` = tb_user.`id`" +
            "${ew.customSqlSegment}")
    IPage<MusicUpdateInfoToUserVo> getMusicListByPage(IPage<MusicUpdateInfoToUserVo> page,
                                                      @Param("ew") Wrapper wrapper);


    //门户
    @Select("SELECT tb_music.id AS music_id,\n" +
            "           tb_music.`name` AS music_name,\n" +
            "           tb_music.`url`,\n" +
            "           tb_music.`file_high_url`,\n" +
            "           tb_music.`create_time`,\n" +
            "           tb_music.`duration`," +
            "           tb_music.`pic_id`," +
            "           tb_music.`singer_name` AS singer_name  FROM tb_music LEFT JOIN tb_singer ON tb_singer.`id` = tb_music.`singer_id`" +
            "${ew.customSqlSegment}")
    IPage<MusicItem> getMusicList(IPage<MusicItem> page,
                                  @Param("ew") Wrapper wrapper);

    @Update("UPDATE `tb_music` set `state`=3 where `id` = #{id}")
    int updateMusicAddTop(@Param("id")String id);
}
