package com.chen.music.mapper;

import com.chen.music.pojo.Friends;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.music.pojo.FriendsToUser;
import com.chen.music.pojo.vo.FriendLinkAndUserVo;
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
public interface FriendsDao extends BaseMapper<Friends> {
    @Update("UPDATE `tb_friends` set `state`=0 where `id` = #{id}")
    int deleteByFriendId(@Param("id")String id);

    @Select("SELECT tb_friends.id,\n" +
            "\ttb_friends.`name` AS friend_name,\n" +
            "\ttb_friends.`logo`,\n" +
            "\ttb_friends.`url`,\n" +
            "\ttb_friends.`order`,\n" +
            "\ttb_user.`avatar`,\n" +
            "\ttb_user.`user_name`,\n" +
            "\ttb_user.`id` AS user_id\n" +
            " FROM tb_friends LEFT JOIN tb_user ON tb_friends.`user_id` = tb_user.`id`\n" +
            "\tWHERE tb_friends.`state` !=0")
    List<FriendLinkAndUserVo> getList();
}
