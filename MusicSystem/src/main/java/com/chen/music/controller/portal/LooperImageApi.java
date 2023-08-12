package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.ILooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/looper")
public class LooperImageApi {

    @Autowired
    private ILooperService looperService;

    @PostMapping("/list")
    public ResponseResult listLoops(){
        return looperService.listLoops();
    }

}
