package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web_size_info")
public class WebSizeInfoApi {

    @Autowired
    private ISettingsService webSizeInfoService;

//    @PutMapping("/view_count")
//    public void updateViewCount(){
//        webSizeInfoService.updateViewCount();
//    }

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle(){
        return webSizeInfoService.getWebSizeTitle();
    }
}
