package com.chen.music.controller.admin;

import com.chen.music.pojo.Looper;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ILooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/looper")
public class LooperImageAdminApi {

    @Autowired
    private ILooperService looperService;

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper){
        return looperService.addLoop(looper);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLooper(@PathVariable("looperId")String looperId){
        return looperService.deleteLooper(looperId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PutMapping("/{looperId}")
    public ResponseResult updateLooper(@PathVariable("looperId")String looperId,@RequestBody Looper looper){
        return looperService.updateLoop(looperId,looper);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @GetMapping("/{looperId}")
    public ResponseResult getLooper(@PathVariable("looperId")String looperId){
        return looperService.getLooper(looperId);
    }

    @PostMapping("/list")
    public ResponseResult listLoops(){
        return looperService.listLoops();
    }

}
