package com.chen.music.service;

import com.chen.music.pojo.Settings;
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
public interface ISettingsService extends IService<Settings> {

    ResponseResult getWebSizeTitle();

    ResponseResult putWebSizeTitle(String title);

    ResponseResult getSizeViewCount();

    void updateViewCount();

    void updateTodayViewCount();

    void updateHistoryViewCount();

    ResponseResult getSevenHistory();

    ResponseResult getMusicCount();

    ResponseResult getMusicerCount();

    ResponseResult getUserCount();
}
