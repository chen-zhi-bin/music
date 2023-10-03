package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.mapper.CollectionDao;
import com.chen.music.mapper.MusicDao;
import com.chen.music.pojo.Collection;
import com.chen.music.pojo.Music;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IAssessorService;
import com.chen.music.utils.CheckUtils;
import com.chen.music.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class AssessorServiceImpl extends ServiceImpl<MusicDao, Music> implements IAssessorService {

    @Autowired
    private MusicDao musicDao;

    @Override
    public ResponseResult getCheckList(int page, int size) {
        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        Page<MusicUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        musicAndSingerVoQueryWrapper.eq("tb_music.`state`", Constants.Music.STATE_AUDITED);
        IPage<MusicUpdateInfoToUserVo> musicList = musicDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult refuseMusic(String musicId) {
        Music music = musicDao.selectById(musicId);
        if (music == null) {
            return ResponseResult.FAILED("音乐不存在");
        }

        music.setState(Constants.Music.STATE_REFUSE);

        QueryWrapper<Music> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("id",musicId);
        int res = musicDao.update(music, updateWrapper);
        return res>0?ResponseResult.SUCCESS("修改成功"):ResponseResult.FAILED("修改失败，音乐不存在");
    }

    @Override
    public ResponseResult refuseList(int page, int size) {
        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        Page<MusicUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        musicAndSingerVoQueryWrapper.eq("tb_music.`state`", Constants.Music.STATE_REFUSE);
        IPage<MusicUpdateInfoToUserVo> musicList = musicDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }
}
