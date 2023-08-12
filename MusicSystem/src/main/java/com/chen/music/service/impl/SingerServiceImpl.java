package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.music.mapper.MusicianInfoDao;
import com.chen.music.pojo.MusicianInfo;
import com.chen.music.pojo.Singer;
import com.chen.music.mapper.SingerDao;
import com.chen.music.pojo.User;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import com.chen.music.pojo.vo.MusicianUpdateInfoToUserVo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.CheckUtils;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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
    private MusicianInfoDao musicianInfoDao;

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
        singer.setState("1");
        singerDao.insert(singer);

        return ResponseResult.SUCCESS("添加成功");
    }

    @Override
    public ResponseResult deleteSingerById(String singerId) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Singer singer = singerDao.selectById(singerId);
        if (singer == null) {
            return ResponseResult.FAILED("音乐人不存在");
        }
        singer.setState("0");
        singer.setUserId(user.getId());
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.eq("id",singer);
        int res = singerDao.update(singer, singerQueryWrapper);
        return res>0?ResponseResult.SUCCESS("删除成功"):ResponseResult.FAILED("删除失败");
    }

    @Override
    public ResponseResult recoverSingerById(String id) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Singer singer = singerDao.selectById(id);
        if (singer == null) {
            return ResponseResult.FAILED("音乐人不存在");
        }
        singer.setUpdateTime(new Date());
        singer.setUserId(user.getId());
        singer.setState(Constants.MUSICIAN.STATE_PUBLISH);
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.eq("id",singer);
        int res = singerDao.update(singer, singerQueryWrapper);
        return res>0?ResponseResult.SUCCESS("恢复成功"):ResponseResult.FAILED("恢复失败");
    }

    @Override
    public ResponseResult getMusicianDeletedList(int page, int size) {
        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        Page<MusicianUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page-1, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicianUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        musicAndSingerVoQueryWrapper.eq("tb_singer.`state`",Constants.Music.STATE_DELETE);
        IPage<MusicianUpdateInfoToUserVo> musicList = singerDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    //普通用户使用
    @Override
    public ResponseResult getMusicianList(int page, int size) {
        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        Page<MusicianInfo> musicianInfoPage = new Page<>(page - 1, size);
        musicianInfoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicianInfo> musicianInfoQueryWrapper = new QueryWrapper<>();
        musicianInfoQueryWrapper.ne("`state`","0");
        Page<MusicianInfo> resPage = musicianInfoDao.selectPage(musicianInfoPage, musicianInfoQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",resPage.getRecords());
        map.put("maxPage",resPage.getPages());
        map.put("current",page);
        map.put("hasMore",resPage.getPages()>page);
        return ResponseResult.SUCCESS("获取音乐人列表成功").setData(map);
    }

    //admin使用
    @Override
    public ResponseResult musicianList(int page, int size) {
        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        Page<MusicianUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page-1, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicianUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        IPage<MusicianUpdateInfoToUserVo> musicList = singerDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }
}
