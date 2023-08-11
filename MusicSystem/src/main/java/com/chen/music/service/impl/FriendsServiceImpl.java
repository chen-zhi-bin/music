package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.music.mapper.FriendsToUserDao;
import com.chen.music.pojo.Friends;
import com.chen.music.mapper.FriendsDao;
import com.chen.music.pojo.FriendsToUser;
import com.chen.music.pojo.Music;
import com.chen.music.pojo.User;
import com.chen.music.pojo.vo.FriendLinkAndUserVo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsDao, Friends> implements IFriendsService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private FriendsDao friendLinkDao;

    @Autowired
    private FriendsToUserDao friendsToUserDao;

    @Autowired
    private IUserService userService;

    @Override
    public ResponseResult addFriendLink(Friends friendLink) {
        User user = userService.checkUser();
        friendLink.setId(idWorker.nextId()+"");
        friendLink.setUserId(user.getId());
        friendLink.setState("1");
        friendLinkDao.insert(friendLink);
        return ResponseResult.SUCCESS("添加成功");
    }


    @Override
    public ResponseResult deleteFriendLink(String friendId) {
        int result = friendLinkDao.deleteById(friendId);
        if (result>0){
            return ResponseResult.SUCCESS("删除成功");
        }
        return ResponseResult.FAILED("删除失败，友链不存在");
    }

    @Override
    public ResponseResult updateFriendLink(String friendLinkId, Friends friendLink) {
        Friends friendLinkFromDb = friendLinkDao.selectById(friendLinkId);
        if (friendLinkFromDb == null) {
            return ResponseResult.FAILED("更新失败");
        }
        String logo = friendLink.getLogo();
        if (!TextUtils.isEmpty(logo)) {
            friendLinkFromDb.setLogo(logo);
        }
        String name = friendLink.getName();
        if (!TextUtils.isEmpty(name)) {
            friendLinkFromDb.setName(name);
        }
        String url = friendLink.getUrl();
        if (!TextUtils.isEmpty(url)) {
            friendLinkFromDb.setUrl(url);
        }
        friendLinkFromDb.setOrder(friendLink.getOrder());
        friendLinkFromDb.setUpdateTime(new Date());
        friendLinkDao.updateById(friendLinkFromDb);
        return ResponseResult.SUCCESS("更新成功");
    }

    @Override
    public ResponseResult getList() {
        User user = userService.checkUser();
        if (user.getRoleId().equals(Constants.User.ROLE_NORMAL)) {
            QueryWrapper<FriendsToUser> friendsToUserQueryWrapper = new QueryWrapper<>();
            friendsToUserQueryWrapper.eq("state","1");
            List<FriendsToUser> friendsToUsers = friendsToUserDao.selectList(friendsToUserQueryWrapper);
            HashMap<String, Object> res = new HashMap<>();
            res.put("list",friendsToUsers);
            return ResponseResult.SUCCESS("获取成功").setData(res);
        }else {
            List<FriendLinkAndUserVo> res = friendLinkDao.getList();
            HashMap<String, Object> resMap = new HashMap<>();
            resMap.put("list",res);
            return ResponseResult.SUCCESS("获取成功").setData(resMap);
        }
    }

}
