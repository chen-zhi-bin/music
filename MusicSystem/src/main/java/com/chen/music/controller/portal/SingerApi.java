package com.chen.music.controller.portal;

import com.chen.music.pojo.Singer;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISingerService;
import com.chen.music.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/singer")
public class SingerApi {

    @Autowired
    private ISingerService iSingerService;



    @GetMapping("/list/{page}/{size}")
    public ResponseResult getSingerList(@PathVariable("page") int page,
                                        @PathVariable("size") int size){
        return iSingerService.getMusicianList(page,size);
    }
}
