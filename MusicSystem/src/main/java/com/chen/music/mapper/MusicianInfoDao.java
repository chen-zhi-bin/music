package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chen.music.pojo.MusicianInfo;
import com.chen.music.pojo.Singer;
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
public interface MusicianInfoDao extends BaseMapper<MusicianInfo> {


}
