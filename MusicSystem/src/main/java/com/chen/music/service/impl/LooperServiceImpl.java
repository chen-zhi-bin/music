package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.music.pojo.Looper;
import com.chen.music.mapper.LooperDao;
import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ILooperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.TextUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class LooperServiceImpl extends ServiceImpl<LooperDao, Looper> implements ILooperService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private LooperDao loopDao;

    @Autowired
    private IUserService userService;

    @Override
    public ResponseResult addLoop(Looper looper) {
        //检查数据
        String title = looper.getTitle();
        if (TextUtils.isEmpty(title)) {
            return ResponseResult.FAILED("标题不可以为空");
        }
        String imageUrl = looper.getImageUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            return ResponseResult.FAILED("图片不可以为空");
        }
        String targetUrl = looper.getTargetUrl();
        if (TextUtils.isEmpty(targetUrl)) {
            return ResponseResult.FAILED("跳转链接不可以为空");
        }
        User user = userService.checkUser();
        //补充数据
        looper.setId(idWorker.nextId()+"");
        looper.setUserId(user.getId());
        looper.setState("1");
        //保存数据
        loopDao.insert(looper);
        //返回结果
        return ResponseResult.SUCCESS("添加成功");
    }

    @Override
    public ResponseResult deleteLooper(String looperId) {
        int res = loopDao.deleteById(looperId);
        return res>0?ResponseResult.SUCCESS("删除成功"):ResponseResult.FAILED("轮播图不存在");
    }

    @Override
    public ResponseResult updateLoop(String looperId, Looper looper) {
        Looper loopFromDb = loopDao.selectById(looperId);
        if (loopFromDb == null) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        //不可以为空的，要判空
        String title = looper.getTitle();
        if (!TextUtils.isEmpty(title)) {
            loopFromDb.setTitle(title);
        }
        String targetUrl = looper.getTargetUrl();
        if (!TextUtils.isEmpty(targetUrl)) {
            loopFromDb.setTargetUrl(targetUrl);
        }
        String imageUrl = looper.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {
            loopFromDb.setImageUrl(imageUrl);
        }
        if (!TextUtils.isEmpty(looper.getState())) {
            loopFromDb.setState(looper.getState());
        }
        loopFromDb.setOrder(looper.getOrder());
        int res = loopDao.updateById(loopFromDb);
        return res>0?ResponseResult.SUCCESS("更新成功"):ResponseResult.FAILED("更新失败");
    }

    @Override
    public ResponseResult getLooper(String looperId) {
        Looper looper = loopDao.selectById(looperId);
        if (looper == null) {
            return ResponseResult.FAILED("获取失败，该轮播图不存在");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("info",looper);
        return ResponseResult.SUCCESS("获取成功").setData(map);
    }

    @Override
    public ResponseResult listLoops() {
        QueryWrapper<Looper> looperQueryWrapper = new QueryWrapper<>();
        looperQueryWrapper.orderByDesc("update_time");
        List<Looper> looperList = loopDao.selectList(looperQueryWrapper);
        if (looperList != null&&looperList.size()>0) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("list",looperList);
            return ResponseResult.SUCCESS("获取成功").setData(res);
        }
        return ResponseResult.FAILED("获取失败，请稍后重试");
    }


}
