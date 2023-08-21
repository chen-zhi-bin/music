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

    @GetMapping("/music/{musicId}/{page}/{size}")
    public ResponseResult listCommentsByMusic(@PathVariable("musicId")String musicId,@PathVariable("page")int page,@PathVariable("size")int size){
        return commentService.listCommentsByMusicId(musicId,page,size);
    }

    @GetMapping("/comment/{commentId}/{page}/{size}")
    public ResponseResult listCommentsByComment(@PathVariable("commentId")String commentId,@PathVariable("page")int page,@PathVariable("size")int size){
        return commentService.listCommentsByCommentId(commentId,page,size);
    }

    @GetMapping("/list/{page}/{size}")
    public ResponseResult getCommentList(@PathVariable("page")int page,@PathVariable("size")int size){
        return commentService.listComments(page,size);
    }

}
