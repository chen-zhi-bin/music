package com.chen.music.service;

import com.chen.music.pojo.Friends;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface IFriendsService extends IService<Friends> {

    ResponseResult addFriendLink(Friends friendLink);

    ResponseResult deleteFriendLink(String friendId);

    ResponseResult updateFriendLink(String friendLinkId, Friends friendLink);

    ResponseResult getList();
}
