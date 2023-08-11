package com.chen.music.mapper;

import com.chen.music.pojo.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface ImageDao extends BaseMapper<Image> {

    @Update("UPDATE `tb_images` set `state`=0 where `id` = #{image_id}")
    int deleteByImageId(@Param("image_id") String imageId);
}
