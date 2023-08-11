package com.chen.music.service.impl;

import com.chen.music.mapper.ImageDao;
import com.chen.music.pojo.Singer;
import com.chen.music.mapper.SingerDao;
import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerDao, Singer> implements ISingerService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IUserService userService;



    @Autowired
    private SingerDao singerDao;


    @Override
    public ResponseResult uploadSinger(Singer singer) {
        User user = userService.checkUser();
        if (user.getId() == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        singer.setId(idWorker.nextId()+"");
        if (singer.getName() == null) {
            return ResponseResult.FAILED("歌手名字不能为空");
        }
        if (singer.getSex() == null) {
            singer.setSex(3);
        }
        if (singer.getPicId() == null) {
            return ResponseResult.FAILED("图片不能为空");
        }
        singer.setUserId(user.getId());
        singerDao.insert(singer);

        return ResponseResult.SUCCESS("添加成功");
    }
}
