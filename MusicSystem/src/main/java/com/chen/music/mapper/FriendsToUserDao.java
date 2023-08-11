package com.chen.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.music.pojo.Friends;
import com.chen.music.pojo.FriendsToUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface FriendsToUserDao extends BaseMapper<FriendsToUser> {

}
