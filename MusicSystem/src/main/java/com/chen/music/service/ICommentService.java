package com.chen.music.service;

import com.chen.music.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface ICommentService extends IService<Comment> {

    ResponseResult deleteCommentById(String commentId);

    ResponseResult postComment(Comment comment);

    ResponseResult postSubComment(Comment comment);

    ResponseResult listComments(int page, int size);

    ResponseResult listCommentsByMusicId(String musicId, int page, int size);

    ResponseResult listCommentsByCommentId(String commentId, int page, int size);
}
