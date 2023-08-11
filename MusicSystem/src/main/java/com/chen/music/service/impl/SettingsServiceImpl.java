package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.music.mapper.MusicDao;
import com.chen.music.mapper.SettingsNoDateDao;
import com.chen.music.mapper.SingerDao;
import com.chen.music.pojo.Settings;
import com.chen.music.mapper.SettingsDao;
import com.chen.music.pojo.solrInfo.SettingsNoDate;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.ISettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.RedisUtils;
import com.chen.music.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsDao, Settings> implements ISettingsService {

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private MusicDao musicDao;

    @Autowired
    private SingerDao singerDao;

    @Autowired
    private SettingsNoDateDao settingsNoDateDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult getWebSizeTitle() {
        QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
        settingsQueryWrapper.eq("`key`", Constants.Settings.WEB_SIZE_TITLE);
        Settings settings = settingsDao.selectOne(settingsQueryWrapper);
        HashMap<String, Object> res = new HashMap<>();
        res.put("data",settings);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult putWebSizeTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return ResponseResult.FAILED("网站标题不能为空");
        }
        QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
        settingsQueryWrapper.eq("`key`", Constants.Settings.WEB_SIZE_TITLE);
        Settings titleFromDb = settingsDao.selectOne(settingsQueryWrapper);
        boolean exist =true;
        if (titleFromDb==null){
            exist = false;
            titleFromDb = new Settings();
            titleFromDb.setId(idWorker.nextId()+"");
            titleFromDb.setKey(Constants.Settings.WEB_SIZE_TITLE);
        }
        titleFromDb.setValue(title);
        if (exist) {
            settingsDao.update(titleFromDb,settingsQueryWrapper);
        }else {
            settingsDao.insert(titleFromDb);
        }
        return ResponseResult.SUCCESS("网站标题设置成功");
    }

    @Override
    public ResponseResult getSizeViewCount() {
        //先从redis里拿出来
        String viewCountStr = redisUtils.get(Constants.Settings.WEB_SIZE_VIEW_COUNT)+"";
        QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
        settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_VIEW_COUNT);
        Settings viewCountFromDb = settingsDao.selectOne(settingsQueryWrapper);
        if (viewCountFromDb == null) {
            viewCountFromDb = this.initViewItem(Constants.Settings.WEB_SIZE_VIEW_COUNT);
            viewCountFromDb.setValue("1");
            settingsDao.insert(viewCountFromDb);
        }
        if (TextUtils.isEmpty(viewCountStr)||viewCountStr.equals("null")) {
            viewCountStr = viewCountFromDb.getValue();
            redisUtils.set(Constants.Settings.WEB_SIZE_VIEW_COUNT,viewCountStr);
        }else {
            //把redis里更新到数据库里
            viewCountFromDb.setValue(viewCountStr);
            settingsDao.update(viewCountFromDb,settingsQueryWrapper);
        }
        Map<String,Object> result=new HashMap<>();
        result.put(viewCountFromDb.getKey(),viewCountFromDb.getValue());
        return ResponseResult.SUCCESS("获取网站浏览量成功").setData(result);
    }


    @Override
    public void updateViewCount() {
        //redis的更新时机：
        Object viewCount = redisUtils.get(Constants.Settings.WEB_SIZE_VIEW_COUNT);
        if (viewCount == null) {
            QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_VIEW_COUNT);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_VIEW_COUNT,Long.parseLong(setting.getValue()));
        }else {
            //数字自增
            BigInteger bigInteger = new BigInteger(viewCount + "");
            bigInteger=bigInteger.add(BigInteger.ONE);
            redisUtils.set(Constants.Settings.WEB_SIZE_VIEW_COUNT,bigInteger.toString());

//            long l = Long.parseLong(viewCount + "");
//            l = l+1;
//            System.out.println(one.toString());
//            redisUtils.set(Constants.Settings.WEB_SIZE_VIEW_COUNT,l);
//            redisUtils.incr(Constants.Settings.WEB_SIZE_VIEW_COUNT,1);
        }
    }

    @Override
    public void updateTodayViewCount() {
        Object viewCount = redisUtils.get(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
        if (viewCount == null) {
            QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT,Long.parseLong(setting.getValue()));
        }else {
            //数字自增
            BigInteger bigInteger = new BigInteger(viewCount + "");
            bigInteger=bigInteger.add(BigInteger.ONE);
            redisUtils.set(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT,bigInteger.toString());
        }
    }

    /**
     * 七天数据更新
     */
    @Override
    public void updateHistoryViewCount() {
        //六天前（六天前的写入七天前）
        Object six = redisUtils.get(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
        QueryWrapper<Settings> settingsQueryWrapper = new QueryWrapper<>();
        if (six == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(six + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }


        //五天前
        Object five = redisUtils.get(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(five + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }

        //four
        Object four = redisUtils.get(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(four + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }

        //three
        Object three = redisUtils.get(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(three + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }

        //two
        Object two = redisUtils.get(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(two + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }

        //one
        Object one = redisUtils.get(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT,Long.parseLong(setting.getValue()));
        }else {
            BigInteger bigInteger = new BigInteger(one + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }

        //today
        Object today = redisUtils.get(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
        if (five == null) {
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting = initViewItem(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT);
                settingsDao.insert(setting);
            }else {
                setting.setKey(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
                settingsDao.updateById(setting);
            }
            redisUtils.set(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT,new BigInteger(setting.getValue()));
            redisUtils.set(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT,BigInteger.ZERO.toString());
        }else {
            BigInteger bigInteger = new BigInteger(today + "");
            redisUtils.set(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT,bigInteger.toString());
            redisUtils.set(Constants.Settings.WEB_SIZE_TODAY_VIEW_COUNT,BigInteger.ZERO.toString());
            settingsQueryWrapper.clear();
            settingsQueryWrapper.eq("`key`",Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
            Settings setting = settingsDao.selectOne(settingsQueryWrapper);
            if (setting == null) {
                setting =initViewItem(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
                setting.setValue(bigInteger+"");
                settingsDao.insert(setting);
            }else {
                setting.setValue(bigInteger.toString());
                settingsDao.updateById(setting);
            }
        }
    }

    @Override
    public ResponseResult getSevenHistory() {
        ArrayList<SettingsNoDate> resList = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        keys.add(Constants.Settings.WEB_SIZE_ONE_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_TWO_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_THREE_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_FOUR_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_FIVE_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_SIX_DAY_BEFORE_VIEW_COUNT);
        keys.add(Constants.Settings.WEB_SIZE_SEVEN_DAY_BEFORE_VIEW_COUNT);
        QueryWrapper<Settings> queryWrapper = new QueryWrapper<>();
//        QueryWrapper<SettingsNoDate> queryWrapper = new QueryWrapper<>();

        for (String key : keys) {
            queryWrapper.clear();
            queryWrapper.eq("`key`",key);
            Settings temp = settingsDao.selectOne(queryWrapper);
//            SettingsNoDate temp = settingsNoDateDao.selectOne(queryWrapper);
            if (temp == null) {
                Settings settings = this.initViewItem(key);
                settingsDao.insert(settings);
                redisUtils.set(key,BigInteger.ZERO);
                resList.add(new SettingsNoDate(null,key,"0"));
            }else {
//                resList.add(temp);
                resList.add(new SettingsNoDate(temp.getId(),temp.getKey(),temp.getValue()));
                redisUtils.set(key,temp.getValue());
            }
        }
        HashMap<String, Object> res = new HashMap<>();
        res.put("history",resList);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult getMusicCount() {
        Long count = musicDao.selectCount(null);
        HashMap<String, Object> res = new HashMap<>();
        res.put("count",count);
        return ResponseResult.SUCCESS("获取音乐数量成功").setData(res);
    }

    @Override
    public ResponseResult getMusicerCount() {
        Long count = singerDao.selectCount(null);
        HashMap<String, Object> res = new HashMap<>();
        res.put("count",count);
        return ResponseResult.SUCCESS("获取歌手数量成功").setData(res);
    }


    private Settings initViewItem(String key){
        Settings viewCount = new Settings();
        viewCount.setId(idWorker.nextId()+"");
        viewCount.setKey(key);
        viewCount.setValue("1");
        return viewCount;
    }
}
