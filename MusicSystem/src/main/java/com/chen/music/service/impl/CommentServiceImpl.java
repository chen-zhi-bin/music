package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.music.mapper.MusicDao;
import com.chen.music.mapper.SubCommentBeanDao;
import com.chen.music.mapper.UserDao;
import com.chen.music.pojo.Comment;
import com.chen.music.mapper.CommentDao;
import com.chen.music.pojo.Music;
import com.chen.music.pojo.SubCommentBean;
import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.CheckUtils;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private IUserService userService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SubCommentBeanDao subCommentDao;

    @Autowired
    private MusicDao musicDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseResult deleteCommentById(String commentId) {
        //检查用户角色
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        //把评论找出来，对比用户权限
        Comment comment = commentDao.selectById(commentId);
        if (comment == null) {
            return ResponseResult.FAILED("评论不存在");
        }
        //登录要判断角色
        if (user.getId().equals(comment.getUserId())||
                Constants.User.ROLE_ADMIN_SUPER_ID.equals(user.getRoleId())||
                Constants.User.ROLE_ADMIN_MUSIC_ID.equals(user.getRoleId())) {
            //用户id一样，说明此评论是当前用户的   或者是管理员
            commentDao.deleteById(commentId);
            return ResponseResult.SUCCESS("评论删除成功");
        }else {
            return ResponseResult.PERMISSION_DENIED();
        }
    }

    @Override
    public ResponseResult postComment(Comment comment) {
        //检查用户是否登录
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
//        检查内容
        String musicId = comment.getSongId();
        if (TextUtils.isEmpty(musicId)) {
            return ResponseResult.FAILED("音乐id不可为空");
        }
        Music music = musicDao.selectById(musicId);
        if (music == null||(!music.getState().equals(Constants.Music.STATE_PUBLISH)&&!music.getState().equals(Constants.Music.STATE_TOP))) {
            return ResponseResult.FAILED("音乐不存在");
        }
        String content = comment.getComment();
        if (TextUtils.isEmpty(content)) {
            return ResponseResult.FAILED("评论内容不可以为空");
        }
        comment.setId(idWorker.nextId()+"");
        comment.setAvatar(user.getAvatar());
        comment.setUserId(user.getId());
        comment.setUserName(user.getUserName());
        comment.setState("1");
        commentDao.insert(comment);
        return ResponseResult.SUCCESS("评论成功");
    }

    @Override
    public ResponseResult postSubComment(Comment comment) {
        //检查用户是否登录
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        String content = comment.getComment();
        if (TextUtils.isEmpty(content)) {
            return ResponseResult.FAILED("评论内容不可以为空");
        }
        String parentId = comment.getParentId();
        if (parentId == null) {
            return ResponseResult.FAILED("父评论id不能为null");
        }
        String parentUserId = comment.getParentUserId();
        if (parentUserId == null) {
            return ResponseResult.FAILED("被评论人id不能为空");
        }
        User parentUser = userDao.selectById(parentUserId);
        Comment comment1 = commentDao.selectById(parentId);
        comment.setId(idWorker.nextId()+"");
        comment.setAvatar(user.getAvatar());
        comment.setUserId(user.getId());
        comment.setUserName(user.getUserName());
        comment.setState("1");
        comment.setSongId(comment1.getSongId());
        comment.setParentUserName(parentUser.getUserName());
        comment.setParentUserAvatar(parentUser.getAvatar());
        comment.setParentUserId(parentUserId);
        commentDao.insert(comment);
        return ResponseResult.SUCCESS("评论成功");
    }

    /**
     * 父评论列表
     * @param page
     * @param size
     * @return
     */
    @Override
    public ResponseResult listComments(int page, int size) {
        page = CheckUtils.checkPage(page);
        size = CheckUtils.checkSize(size);
        Page<Comment> commentPage = new Page<>(page - 1, size);
        commentPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        //查非子评论
        commentQueryWrapper.isNull("parent_id");
        Page<Comment> resPage = commentDao.selectPage(commentPage, commentQueryWrapper);
        List<Comment> comments = resPage.getRecords();

        QueryWrapper<SubCommentBean> subCommentQueryWrapper = new QueryWrapper<>();

        for (Comment comment : comments) {
            subCommentQueryWrapper.clear();
            subCommentQueryWrapper.eq("parent_id",comment.getId());
            Long count = subCommentDao.selectCount(subCommentQueryWrapper);
            HashMap<String, Object> map = new HashMap<>();
            map.put("count",count);
            comment.setSubComments(map);
        }

        HashMap<String, Object> resMap = new HashMap<>();
        long currentPage = resPage.getCurrent();
        long maxPage = resPage.getPages();
        resMap.put("list", comments);
        resMap.put("currentPage",currentPage);
        resMap.put("maxPage",maxPage);
        return ResponseResult.SUCCESS("获取父评论成功").setData(resMap);
    }

    @Override
    public ResponseResult listCommentsByMusicId(String musicId, int page, int size) {
        page = CheckUtils.checkPage(page);
        size = CheckUtils.checkSize(size);
        Page<Comment> commentPage = new Page<>(page - 1, size);
        commentPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();

        commentQueryWrapper.eq("song_id",musicId);
        commentQueryWrapper.isNull("parent_id");
        Page<Comment> resPage = commentDao.selectPage(commentPage, commentQueryWrapper);
        List<Comment> commentList = resPage.getRecords();

        QueryWrapper<SubCommentBean> subCommentQueryWrapper = new QueryWrapper<>();
        Page<SubCommentBean> subCommentPage = new Page<>(0, Constants.Music.PREDICT_SUB_COMMENT_SIZE);
        for (Comment comment : commentList) {
            subCommentQueryWrapper.clear();
            subCommentQueryWrapper.eq("parent_id",comment.getId());
            Page<SubCommentBean> subCommentPage1 = subCommentDao.selectPage(subCommentPage, subCommentQueryWrapper);
            HashMap<String, Object> map = new HashMap<>();
            map.put("subComment",subCommentPage1.getRecords());
            long total = subCommentPage1.getTotal();
            map.put("hasMore", total >Constants.Music.PREDICT_SUB_COMMENT_SIZE);
            map.put("totalCount", total);
            comment.setSubComments(map);
        }

        HashMap<String, Object> resMap = new HashMap<>();
        long currentPage = resPage.getCurrent();
        long maxPage = resPage.getPages();
        resMap.put("list",commentList);
        resMap.put("currentPage",currentPage);
        resMap.put("maxPage",maxPage);
        return ResponseResult.SUCCESS("获取成功").setData(resMap);
    }

    @Override
    public ResponseResult listCommentsByCommentId(String commentId, int page, int size) {
        page = CheckUtils.checkPage(page);
        size = CheckUtils.checkSize(size);
        Page<SubCommentBean> subCommentBeanPage = new Page<>(page-1,size);
        QueryWrapper<SubCommentBean> subCommentBeanQueryWrapper = new QueryWrapper<>();
        subCommentBeanQueryWrapper.eq("parent_id",commentId);
        Page<SubCommentBean> selectPage = subCommentDao.selectPage(subCommentBeanPage, subCommentBeanQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",selectPage.getRecords());
        long total = selectPage.getTotal();
        res.put("hasMore",total>Constants.Music.PREDICT_SUB_COMMENT_SIZE);
        res.put("titalPage",selectPage.getPages());
        res.put("curentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }


}
