package com.chen.music.service;

import com.chen.music.pojo.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface IMusicService extends IService<Music> {

    ResponseResult uploadMusic(MultipartFile file);

    ResponseResult deleteMusic(String id);

    ResponseResult recoverMusic(String id);

    void viewMusic(HttpServletResponse response, String musicId) throws IOException;

    ResponseResult updateMusic(String musicId, MultipartFile file);

    ResponseResult updateMusic(String musicId, Music music);

    ResponseResult getMusicInfo(String musicId);

    ResponseResult adminListMusics(int page, int size,String state);

    ResponseResult doSearchMusicByName(int page, int size, String name);

    ResponseResult doSearchMusicByLyric(int page, int size, String lyric);

    ResponseResult updateMusicTop(String musicId);

    ResponseResult getDeleteList(int page, int size);

    ResponseResult getListBySingId(String musicianId, int page, int size);

    ResponseResult getListMusic(int page, int size, String state);

    ResponseResult getMusicListByMusicianId(String musicianId, int page, int size);

    ResponseResult uploadMusics(MultipartFile[] files);
}
