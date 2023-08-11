package com.chen.music.controller.admin;

import com.chen.music.pojo.Comment;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/comment")
public class CommentAdminApi {

    @Autowired
    private ICommentService commentService;



    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId")String commentId){
        return commentService.deleteCommentById(commentId);
    }

    @PostMapping("/comment")
    public ResponseResult postComment(@RequestBody Comment comment){
        return commentService.postComment(comment);
    }

    @PostMapping("/sub_comment")
    public ResponseResult postSubComment(@RequestBody Comment comment){
        return commentService.postSubComment(comment);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.musicAdmin()")
    @GetMapping("/list")
    public ResponseResult listComments(@RequestParam("page")int page,@RequestParam("size")int size){
        return commentService.listComments(page,size);
    }

    @GetMapping("/music/{musicId}")
    public ResponseResult listCommentsByMusic(@PathVariable("musicId")String musicId,@RequestParam("page")int page,@RequestParam("size")int size){
        return commentService.listCommentsByMusicId(musicId,page,size);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseResult listCommentsByComment(@PathVariable("commentId")String commentId,@RequestParam("page")int page,@RequestParam("size")int size){
        return commentService.listCommentsByCommentId(commentId,page,size);
    }

}
