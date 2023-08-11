package com.chen.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.pojo.Collection;
import com.chen.music.response.ResponseResult;

public interface ICollectionService extends IService<Collection> {
    ResponseResult deleteCollectionById(String collectionId);

    ResponseResult doCollection(String musicId);

    ResponseResult getCollections(int page);
}
