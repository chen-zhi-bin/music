package com.chen.music.mapper;

import com.chen.music.pojo.User;
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
public interface UserDao extends BaseMapper<User> {

    @Update("UPDATE `tb_user` set `deleted`=1 where `id` = #{user_id}")
    int deleteByUserId(@Param("user_id") String userId);

    @Update("UPDATE `tb_user` set `email`= #{email} where `id` = #{user_id}")
    int updateEmail(@Param("user_id") String userId,@Param("email") String email);

}
