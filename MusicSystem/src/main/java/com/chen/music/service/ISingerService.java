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

}
