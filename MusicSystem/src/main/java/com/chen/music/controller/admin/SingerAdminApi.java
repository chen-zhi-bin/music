package com.chen.music.controller.admin;

import com.chen.music.pojo.Singer;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
