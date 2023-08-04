package com.chen.music.service.impl;

import com.chen.music.pojo.Comment;
import com.chen.music.mapper.CommentDao;
import com.chen.music.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements ICommentService {

}
