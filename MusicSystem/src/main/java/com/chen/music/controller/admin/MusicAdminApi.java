package com.chen.music.controller.admin;

import com.chen.music.pojo.Music;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/admin/music")
public class MusicAdminApi {

    @Autowired
    private IMusicService iMusicService;

    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file") MultipartFile file) {
        return iMusicService.uploadMusic(file);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @DeleteMapping("/{musicId}")
    public ResponseResult deleteMusic(@PathVariable("musicId") String id) {
        return iMusicService.deleteMusic(id);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @PutMapping("/recover/{musicId}")
    public ResponseResult recoverMusic(@PathVariable("musicId") String id) {
        return iMusicService.recoverMusic(id);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @PostMapping("/list/{state}/{page}/{size}")
    public ResponseResult listMusics(@PathVariable("page") int page, @PathVariable("size") int size,@PathVariable("state")String state) {
        return iMusicService.adminListMusics(page,size,state);
    }

    @GetMapping("/{musicId}")
    public void getMusic(HttpServletResponse response, @PathVariable("musicId") String musicId) {
        try {
            iMusicService.viewMusic(response,musicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/{musicId}")
    public ResponseResult updateMusic(@PathVariable("musicId") String musicId,
                                      @RequestParam("file") MultipartFile file){
        return iMusicService.updateMusic(musicId,file);
    }

    @PutMapping("/info/{musicId}")
    public ResponseResult updateMusic(@PathVariable("musicId") String musicId,
                                      @RequestBody Music music){
        return iMusicService.updateMusic(musicId,music);
    }

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

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @PostMapping("/top/{musicId}")
    public ResponseResult updateMusicTop(@PathVariable("musicId") String musicId){
        return iMusicService.updateMusicTop(musicId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @GetMapping("/delete/list/{page}/{size}")
    public ResponseResult getDeletedList(@PathVariable("page") int page,
                                         @PathVariable("size") int size){
        return iMusicService.getDeleteList(page,size);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @GetMapping("/musician/list/{musician_Id}/{page}/{size}")
    public ResponseResult getListBySingId(
                                            @PathVariable("musician_Id") String musicianId,
                                            @PathVariable("page") int page,
                                          @PathVariable("size") int size){
        return iMusicService.getListBySingId(musicianId,page,size);
    }
}
