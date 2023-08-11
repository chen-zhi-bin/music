package com.chen.music.controller.admin;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfoAdminApi {
    @Autowired
    private ISettingsService webSizeInfoService;

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/title")
    public ResponseResult getWebSizeTitle(){
        return webSizeInfoService.getWebSizeTitle();
    }

    @PreAuthorize("@permission.superAdmin()")
    @PutMapping("/title")
    public ResponseResult upWebSizeTitle(@RequestParam("title")String title){
        return webSizeInfoService.putWebSizeTitle(title);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/music_count")
    public ResponseResult getMusicCount(){
        return webSizeInfoService.getMusicCount();
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/musicer_count")
    public ResponseResult getMusicer(){
        return webSizeInfoService.getMusicerCount();
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/user_count")
    public ResponseResult getUserCount(){
        return webSizeInfoService.getUserCount();
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount(){
        return webSizeInfoService.getSizeViewCount();
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()||@permission.userAdmin()||@permission.musicAdmin()")
    @GetMapping("/seven_history")
    public ResponseResult getHistory(){
        return webSizeInfoService.getSevenHistory();
    }
}
