package com.chen.music.controller.portal;


import com.chen.music.pojo.Comment;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentApi {

    @Autowired
    private ICommentService commentService;

    @PostMapping("")
    public ResponseResult postComment(@RequestBody Comment comment){
        return commentService.postComment(comment);
    }

    @PostMapping("/sub_comment")
    public ResponseResult postSubComment(@RequestBody Comment comment){
        return commentService.postSubComment(comment);
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
