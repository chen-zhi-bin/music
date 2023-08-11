package com.chen.music.service;

import com.chen.music.pojo.Looper;
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
public interface ILooperService extends IService<Looper> {

    ResponseResult addLoop(Looper looper);

    ResponseResult deleteLooper(String looperId);

    ResponseResult updateLoop(String looperId, Looper looper);

    ResponseResult getLooper(String looperId);

    ResponseResult listLoops();

}
