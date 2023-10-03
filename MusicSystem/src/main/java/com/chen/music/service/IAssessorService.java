package com.chen.music.service;

import com.chen.music.pojo.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;

public interface IAssessorService extends IService<Music> {

    ResponseResult getCheckList(int page, int size);

    ResponseResult refuseMusic(String musicId);

    ResponseResult refuseList(int page, int size);
}
