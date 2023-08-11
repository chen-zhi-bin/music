package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/music")
public class MusicApi {
    @Autowired
    private IMusicService iMusicService;

    @GetMapping("/{musicId}")
    public void getMusic(HttpServletResponse response, @PathVariable("musicId") String musicId) {
        try {
            iMusicService.viewMusic(response,musicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @GetMapping("/list/recommend/{page}/{size}")
//    public ResponseResult listRecommendMusics(@PathVariable("page") int page, @PathVariable("size") int size) {
//        return iMusicService.listmusics(page,size,"1");
//    }

//    @GetMapping("/list/top/{page}/{size}")
//    public ResponseResult listTopMusics(@PathVariable("page") int page, @PathVariable("size") int size) {
//        return iMusicService.listmusics(page,size,"3");
//    }

    @GetMapping("/info/{musicId}")
    public ResponseResult getMusicInfo(@PathVariable("musicId") String musicId){
        return iMusicService.getMusicInfo(musicId);
    }

    @PostMapping("/list/name/{page}/{size}")
    public ResponseResult doSearchMusicByName(@PathVariable("page") int page,
                                              @PathVariable("size") int size,
                                              @RequestParam("name")String name){
        return iMusicService.doSearchMusicByName(page,size,name);
    }

    @PostMapping("/list/lyric/{page}/{size}")
    public ResponseResult doSearchMusicByLyric(@PathVariable("page") int page,
                                               @PathVariable("size") int size,
                                               @RequestParam("lyric")String lyric){
        return iMusicService.doSearchMusicByLyric(page,size,lyric);
    }


}
