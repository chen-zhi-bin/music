package com.chen.music.service;

import com.chen.music.pojo.Singer;
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
public interface ISingerService extends IService<Singer> {

    ResponseResult uploadSinger(Singer singer);

    ResponseResult deleteSingerById(String singerId);

    ResponseResult recoverSingerById(String id);

    ResponseResult getMusicianDeletedList(int page, int size);

    ResponseResult getMusicianList(int page, int size);

    ResponseResult musicianList(int page, int size);

    ResponseResult updateMusicianInfo(String id, Singer singer);
}
