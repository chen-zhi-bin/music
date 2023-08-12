package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.music.pojo.Singer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import com.chen.music.pojo.vo.MusicianUpdateInfoToUserVo;
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
public interface SingerDao extends BaseMapper<Singer> {

    @Select("SELECT\n" +
            "    tb_singer.`id` AS musician_id,\n" +
            "    tb_singer.`name`,\n" +
            "    tb_singer.`sex`,\n" +
            "    tb_singer.`pic_id`,\n" +
            "    tb_singer.`introduction`,\n" +
            "    tb_singer.`user_id`,\n" +
            "    tb_user.`user_name`,\n" +
            "    tb_singer.`create_time`,\n" +
            "    tb_singer.`update_time`,\n" +
            "    tb_singer.`state`\n" +
            "FROM tb_singer JOIN tb_user ON tb_singer.`user_id` = tb_user.`id`" +
            "${ew.customSqlSegment}")
    IPage<MusicianUpdateInfoToUserVo> getMusicListByPage(IPage<MusicianUpdateInfoToUserVo> page,
                                                         @Param("ew") Wrapper wrapper);
}
