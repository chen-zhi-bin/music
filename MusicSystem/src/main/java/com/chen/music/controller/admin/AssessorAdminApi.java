package com.chen.music.controller.admin;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.IAssessorService;
import com.chen.music.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/assessor")
public class AssessorAdminApi {

    @Autowired
    private IAssessorService iAssessorService;

    @Autowired
    private IMusicService iMusicService;

    @PreAuthorize("@permission.superAdmin()||@permission.assessorAdmin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listMusic(@PathVariable("page") int page, @PathVariable("size") int size) {
        return iAssessorService.getCheckList(page,size);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.assessorAdmin()")
    @PostMapping("/pass/{musicId}")
    public ResponseResult passMusic(@PathVariable("musicId") String musicId) {
        return iMusicService.recoverMusic(musicId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.assessorAdmin()")
    @DeleteMapping("/refuse/{musicId}")
    public ResponseResult refuseMusic(@PathVariable("musicId") String musicId){
        return iAssessorService.refuseMusic(musicId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.assessorAdmin()")
    @GetMapping("/refuseList/{page}/{size}")
    public ResponseResult refuseList(@PathVariable("page") int page, @PathVariable("size") int size){
        return iAssessorService.refuseList(page,size);
    }
}
