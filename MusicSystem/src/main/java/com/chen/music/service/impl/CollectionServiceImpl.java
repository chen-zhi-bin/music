package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.mapper.CollectionDao;
import com.chen.music.pojo.Collection;
import com.chen.music.pojo.User;
import com.chen.music.pojo.vo.CollectionVo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ICollectionService;
import com.chen.music.service.IUserService;
import com.chen.music.utils.CheckUtils;
import com.chen.music.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CollectionServiceImpl  extends ServiceImpl<CollectionDao, Collection> implements ICollectionService {

    @Autowired
    private IUserService userService;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult deleteCollectionById(String musicId) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        QueryWrapper<Collection> collectionQueryWrapper = new QueryWrapper<>();
        collectionQueryWrapper.eq("user_id",user.getId());
        collectionQueryWrapper.eq("music_id",musicId);
        List<Collection> collections = collectionDao.selectList(collectionQueryWrapper);
        if (collections==null||collections.size()==0){
            return ResponseResult.FAILED("没有收藏此音乐");
        }
        collectionDao.delete(collectionQueryWrapper);
        return ResponseResult.SUCCESS("取消收藏成功");
    }

    @Override
    public ResponseResult doCollection(String musicId) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        QueryWrapper<Collection> collectionQueryWrapper = new QueryWrapper<>();
        collectionQueryWrapper.eq("user_id",user.getId());
        collectionQueryWrapper.eq("music_id",musicId);
        List<Collection> collections = collectionDao.selectList(collectionQueryWrapper);
        if (collections==null||collections.size()==0){
            Collection collection = new Collection();
            collection.setId(idWorker.nextId()+"");
            collection.setUserId(user.getId());
            collection.setMusicId(musicId);
            collectionDao.insert(collection);
            return ResponseResult.FAILED("收藏此音乐成功");
        }
        return ResponseResult.FAILED("已收藏此音乐");
    }

    @Override
    public ResponseResult getCollections(int page) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        page= CheckUtils.checkPage(page);
        Page<CollectionVo> collectionVoPage = new Page<>(page - 1, 100);
        QueryWrapper<CollectionVo> collectionVoQueryWrapper = new QueryWrapper<>();
        collectionVoQueryWrapper.eq("tb_collection.user_id",user.getId());
        IPage<CollectionVo> musicListByPage = collectionDao.getMusicListByPage(collectionVoPage, collectionVoQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicListByPage.getRecords());
        res.put("totalPage",musicListByPage.getPages());
        res.put("currentPage",page);
        res.put("hasMore",musicListByPage.getPages()>page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }


}
