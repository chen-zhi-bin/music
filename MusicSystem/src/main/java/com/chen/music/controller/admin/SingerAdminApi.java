package com.chen.music.controller.admin;

import com.chen.music.pojo.Singer;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/singer")
public class SingerAdminApi {

    @Autowired
    private ISingerService iSingerService;

    @PostMapping
    public ResponseResult uploadSinger(@RequestBody Singer singer) {
        return iSingerService.uploadSinger(singer);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @DeleteMapping("/{singer_id}")
    public ResponseResult deleteSinger(@PathVariable("singer_id")String singerId) {
        return iSingerService.deleteSingerById(singerId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @PutMapping("/recover/{singer_id}")
    public ResponseResult recoverMusic(@PathVariable("singer_id") String id) {
        return iSingerService.recoverSingerById(id);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @GetMapping("/deletedlist/{page}/{size}")
    public ResponseResult getMusicianDeletedList(@PathVariable("page") int page,
                                                 @PathVariable("size") int size) {
        return iSingerService.getMusicianDeletedList(page,size);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @GetMapping("/{page}/{size}")
    public ResponseResult musicianList(@PathVariable("page") int page,
                                                 @PathVariable("size") int size) {
        return iSingerService.musicianList(page,size);
    }

    @GetMapping("/list/{page}/{size}")
    public ResponseResult getMusicianList(@PathVariable("page") int page,
                                          @PathVariable("size") int size){
        return iSingerService.getMusicianList(page,size);
    }

}
