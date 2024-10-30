package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.music.mapper.SingerDao;
import com.chen.music.pojo.Music;
import com.chen.music.mapper.MusicDao;
import com.chen.music.pojo.Singer;
import com.chen.music.pojo.User;
import com.chen.music.pojo.solrInfo.MusicInfo;
import com.chen.music.pojo.vo.MusicAndSingerVo;
import com.chen.music.pojo.vo.MusicItem;
import com.chen.music.pojo.vo.MusicUpdateInfoToUserVo;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.ISettingsService;
import com.chen.music.service.IUserService;
import com.chen.music.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Slf4j
@Service
public class MusicServiceImpl extends ServiceImpl<MusicDao, Music> implements IMusicService {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MusicDao musicDao;

    @Autowired
    private SingerDao singerDao;

    @Autowired
    private IUserService userService;

    @Value("${music.system.music.max-size}")
    public long maxSize;

    @Value("${music.system.music.save-path-music}")
    public String musicPath;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ISettingsService settingsService;

    @Override
    public ResponseResult uploadMusic(MultipartFile file) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        String md5=null;
        InputStream inputStream = null;
        String contentType = file.getContentType();
        if (TextUtils.isEmpty(contentType)) {
            return ResponseResult.FAILED("音频文件格式错误");
        }
        //        获取相关数据，比如说文件类型，文件名称
        String originalFilename = file.getOriginalFilename();
        String type = getType(contentType, originalFilename);
        try {
            inputStream = file.getInputStream();
            md5= DigestUtils.md5DigestAsHex(inputStream);
            QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<>();
            if (type.equals(Constants.MusicType.TYPE_MP3)){
                musicQueryWrapper.eq("md5",md5);
            }else {
                musicQueryWrapper.eq("file_high_md5",md5);
            }
            Music music = musicDao.selectOne(musicQueryWrapper);
            if (music != null) {
                Map<String,Object> res= new HashMap<>();
                res.put("name",music.getName());
                res.put("url",music.getUrl());
                res.put("id",music.getId());
                res.put("highId",music.getFileHighUrl());
                return ResponseResult.SUCCESS("音频已经存在").setData(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("音频上传失败");
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //判断文件类型
        if (file == null) {
            return ResponseResult.FAILED("文件不可以为空");
        }

        log.info("originalFilename ="+originalFilename+
                "\n type ="+type+
                "\n contentType ="+contentType);
        if (type==null){
            return ResponseResult.FAILED("不支持此文件类型");
        }
        String name = file.getName();
        log.info("name==" + name);
        log.info("originalFilename==" + originalFilename);
        //限制文件大小
        long size = file.getSize();
        if (size>maxSize){
            return ResponseResult.FAILED("最大仅支持"+(maxSize/1204/1024)+"MB");
        }
        //创建图片保存目录
        //规则：配置目录/日期/类型/ID.类型
        long currentMillions = System.currentTimeMillis();
        String currentDay = simpleDateFormat.format(currentMillions);
        log.info("current day = >" + currentDay);
        String dayPath = musicPath + File.separator + currentDay;
        File dayPathFile = new File(dayPath);
        //判断日期文件夹是否存在
        if (!dayPathFile.exists()){
            dayPathFile.getParentFile().mkdirs();
        }
        String targetName = String.valueOf(idWorker.nextId());
        String targetPath = dayPath+
                File.separator+type+File.separator+ targetName +"."+type;
        File targetFile = new File(targetPath);
        //判断类型文件夹是否存在
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
//            targetFile.mkdirs();
        }
//        保存文件
        try {
            file.transferTo(targetFile);
            //保存记录到数据库
            //返回结果：包含这个图片的名称和访问路径
//            第一个是访问路径  --》得对应着来解析
            Map<String,Object> result = new HashMap<>();
            String resultPath = currentMillions + "_" + targetName + "." + type;
            result.put("url",resultPath);
            result.put("id",targetName);
            //            第二个是名称--》alt="图片描述",如果不写，前端可以通过名称作为这个描述
            result.put("name",originalFilename);
            Music music = new Music();
            music.setId(targetName);
            music.setContentType(type);
            if (type.equals(Constants.MusicType.TYPE_FLAC)){
                music.setFileHighPath(targetFile.getPath());
                music.setFileHighMd5(md5);
                music.setFileHighUrl(resultPath);
            }else {
                music.setPath(targetFile.getPath());
                music.setUrl(resultPath);
                music.setMd5(md5);
            }
            music.setName(originalFilename);
            music.setState("1");
            music.setUserId(user.getId());

            File audioFile = null;
            if (type.equals(Constants.MusicType.TYPE_MP3)){
                audioFile = new File(music.getPath());
            }else {
                audioFile = new File(music.getFileHighPath());
            }
            Float mp3Duration = AudioUtil.getMp3Duration(audioFile);

//            Parser parser = new AutoDetectParser();
//            BodyContentHandler handler = new BodyContentHandler();
//            Metadata metadata = new Metadata();
//
//            FileInputStream inputStreamTemp = new FileInputStream(audioFile);
//            parser.parse(inputStreamTemp, handler, metadata,new ParseContext());
//            inputStream.close();
//
//            String duration = metadata.get("xmpDM:duration");
//            if (duration == null) {
//                // 尝试其他字段名
////                 duration = metadata.get("Duration");
//                 duration = metadata.get("length");
//            }
//            log.info(metadata.get("xmpDM:duration"));
//            log.info(metadata.get("Duration"));
//            log.info(metadata.get("length"));
//            // 将微秒转换为分钟和秒
//            long milliseconds = Long.parseLong(duration);
//            long minutes = milliseconds / (60 * 1000);
//            long seconds = (milliseconds / 1000) % 60;
            music.setDuration(mp3Duration+"");
            musicDao.insert(music);
            //返回结果
            return ResponseResult.SUCCESS("上传成功").setData(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.FAILED("上传失败，请稍后重试");
    }

    @Override
    public ResponseResult deleteMusic(String id) {
        User user = userService.checkUser();
        if (user == null||(!user.getRoleId().equals("1")&&!user.equals("4"))) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Music music = musicDao.selectById(id);
        if (music == null) {
            return ResponseResult.FAILED("音乐不存在");
        }
        music.setUpdateTime(new Date());
        music.setUserId(user.getId());
        music.setState(Constants.Music.STATE_DELETE);
        QueryWrapper<Music> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("id",id);
        int res = musicDao.update(music, updateWrapper);
        return res>0?ResponseResult.SUCCESS("删除成功"):ResponseResult.FAILED("删除失败，音乐不存在");
    }

    @Override
    public ResponseResult recoverMusic(String id) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Music music = musicDao.selectById(id);
        if (music == null) {
            return ResponseResult.FAILED("音乐不存在");
        }
        music.setUpdateTime(new Date());
        music.setUserId(user.getId());
        music.setState(Constants.Music.STATE_PUBLISH);
        QueryWrapper<Music> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("id",id);
        int res = musicDao.update(music, updateWrapper);
        return res>0?ResponseResult.SUCCESS("修改成功"):ResponseResult.FAILED("修改失败");
    }

    @Override
    public void viewMusic(HttpServletResponse response, String musicId) throws IOException {
        String[] paths = musicId.split("_");
        String dayValue = paths[0];
        String format = simpleDateFormat.format(Long.parseLong(dayValue));
        String name = paths[1];
        String[] nameSplit = name.split("\\.");
        String type = nameSplit[1];
        String targetPath = musicPath + File.separator + format + File.separator +
                type+
                File.separator +name;
        log.info("get image target path ==> "+targetPath);
        File file = new File(targetPath);
        OutputStream writer = null;
        FileInputStream fos = null;
        try {
            if (type.equals(Constants.MusicType.TYPE_MP3)){
                response.setContentType(Constants.MusicType.TYPE_MP3_WITH_PREFIX_AUDIO);
                System.out.println(Constants.MusicType.TYPE_MP3_WITH_PREFIX_AUDIO);
            }else if (type.equals(Constants.MusicType.TYPE_FLAC)){
                response.setContentType(Constants.MusicType.TYPE_FLAC_WITH_PREFIX_AUDIO);
                System.out.println(Constants.MusicType.TYPE_FLAC_WITH_PREFIX_AUDIO);
            }
            writer = response.getOutputStream();
            //读取
            fos = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fos.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        settingsService.updateViewCount();
        settingsService.updateTodayViewCount();
    }

    @Override
    public ResponseResult updateMusic(String musicId,MultipartFile file) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Music music = musicDao.selectById(musicId);
        if (!user.getId().equals(music.getUserId())&&
                !user.getRoleId().equals(Constants.User.ROLE_ADMIN_SUPER_ID)&&
                !user.getRoleId().equals(Constants.User.ROLE_ADMIN_MUSIC_ID)) {
            return ResponseResult.PERMISSION_DENIED();
        }
        String md5=null;
        InputStream inputStream = null;
        String contentType = file.getContentType();
        if (TextUtils.isEmpty(contentType)) {
            return ResponseResult.FAILED("音频文件格式错误");
        }
        //        获取相关数据，比如说文件类型，文件名称
        String originalFilename = file.getOriginalFilename();
        String type = getType(contentType, originalFilename);
        try {
            inputStream = file.getInputStream();
            md5= DigestUtils.md5DigestAsHex(inputStream);
            QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<>();
            if (type.equals(Constants.MusicType.TYPE_MP3)){
                musicQueryWrapper.eq("md5",md5);
            }else {
                musicQueryWrapper.eq("file_high_md5",md5);
            }
            Music musicFromDb = musicDao.selectOne(musicQueryWrapper);
            if (musicFromDb != null) {
                Map<String,Object> res= new HashMap<>();
                res.put("name",musicFromDb.getName());
                res.put("id",musicFromDb.getUrl());
                res.put("highId",musicFromDb.getFileHighUrl());
                return ResponseResult.SUCCESS("音频已经存在").setData(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("音频上传失败");
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //判断文件类型
//        if (file == null) {
//            return ResponseResult.FAILED("文件不可以为空");
//        }

        log.info("originalFilename ="+originalFilename+
                "\n type ="+type+
                "\n contentType ="+contentType);
//        if (type==null){
//            return ResponseResult.FAILED("不支持此文件类型");
//        }
        String name = file.getName();
        log.info("name==" + name);
        log.info("originalFilename==" + originalFilename);
        //限制文件大小
        long size = file.getSize();
        if (size>maxSize){
            return ResponseResult.FAILED("图片最大仅支持"+(maxSize/1204/1024)+"MB");
        }
        //创建图片保存目录
        //规则：配置目录/日期/类型/ID.类型
        long currentMillions = System.currentTimeMillis();
        String currentDay = simpleDateFormat.format(currentMillions);
        log.info("current day = >" + currentDay);
        String dayPath = musicPath + File.separator + currentDay;
        File dayPathFile = new File(dayPath);
        //判断日期文件夹是否存在
        if (!dayPathFile.exists()){
            dayPathFile.getParentFile().mkdirs();
        }
        String targetName = String.valueOf(idWorker.nextId());
        String targetPath = dayPath+
                File.separator+type+File.separator+ targetName +"."+type;
        File targetFile = new File(targetPath);
        //判断类型文件夹是否存在
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
//            targetFile.mkdirs();
        }
//        保存文件
        try {
            file.transferTo(targetFile);
            //保存记录到数据库
            //返回结果：包含这个图片的名称和访问路径
//            第一个是访问路径  --》得对应着来解析
            Map<String,Object> result = new HashMap<>();
            String resultPath = currentMillions + "_" + targetName + "." + type;
            result.put("id",resultPath);
            //            第二个是名称--》alt="图片描述",如果不写，前端可以通过名称作为这个描述
            result.put("name",originalFilename);
            if (type.equals(Constants.MusicType.TYPE_FLAC)){
                music.setFileHighPath(targetFile.getPath());
                music.setFileHighMd5(md5);
                music.setFileHighUrl(resultPath);
            }else {
                music.setPath(targetFile.getPath());
                music.setUrl(resultPath);
                music.setMd5(md5);
            }
            music.setContentType(type);
            if (music.getUrl()!=null&&music.getFileHighUrl()!=null){
                music.setContentType(Constants.MusicType.TYPE_MP3_AND_FLAC);
            }
            musicDao.updateById(music);
            //返回结果
            return ResponseResult.SUCCESS("更新成功").setData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.FAILED("更新失败，请稍后重试");
    }

    @Override
    public ResponseResult updateMusic(String musicId, Music music) {
        User user = userService.checkUser();
        String userId = user.getId();
        if (!userId.equals(music.getUserId())&&
                !user.getRoleId().equals(Constants.User.ROLE_ADMIN_SUPER_ID)&&
                !user.getRoleId().equals(Constants.User.ROLE_ADMIN_MUSIC_ID)) {
            return ResponseResult.PERMISSION_DENIED();
        }
        Music music1 = musicDao.selectById(musicId);
        if (music.getLyric() != null) {
            music1.setLyric(music.getLyric());
        }
        if (music.getName() != null) {
            music1.setName(music.getName());
        }
        if (music.getSingerId() != null) {
            music1.setSingerId(music.getSingerId());
        }

        if (music.getIntroduction() != null) {
            music1.setIntroduction(music.getIntroduction());
        }
        if (music.getPicId() != null) {
            music1.setPicId(music.getPicId());
        }
        if (music.getState() != null) {
            music1.setState(music.getState());
        }
        if (music.getSingerName() != null) {
            music1.setSingerName(music.getSingerName());
        }
        music1.setUserId(userId);
        music1.setUpdateTime(new Date());
        musicDao.updateById(music1);
        return ResponseResult.SUCCESS("更新成功");
    }

    @Override
    public ResponseResult getMusicInfo(String musicId) {
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<>();
        musicQueryWrapper.ne("state","0");
        musicQueryWrapper.eq("id",musicId);
        Music music = musicDao.selectOne(musicQueryWrapper);
        if (music == null) {
            return ResponseResult.FAILED("音乐并未收录");
        }
        Map<String, Object> res = new HashMap<>();
        String singerId = music.getSingerId();
        List<Singer> singers = new ArrayList<>();
        if (singerId != null) {
            if (singerId.length()>19){
                String[] split = singerId.split("-");
                for (String s : split) {
                    Singer singer = singerDao.selectById(s);
                    if (singer != null) {
                        singers.add(singer);
                    }
                }
            }else {
                Singer singer = singerDao.selectById(singerId);
                if (singer != null) {
                    singers.add(singer);
                }
            }
        }
        res.put("musicInfo",music);
        res.put("singer",singers);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }


    /**
     * 获取非删除的列表
     * @param page
     * @param size
     * @param state
     * @return
     */
    @Override
    public ResponseResult adminListMusics(int page, int size,String state) {
        page=CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);

        Page<MusicUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        if (!state.equals("all")){
            musicAndSingerVoQueryWrapper.eq("tb_music.`state`",state);
        }
        IPage<MusicUpdateInfoToUserVo> musicList = musicDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        log.info(musicList.toString());
//        log.info(offset+"");
//        log.info(size+"");
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult doSearchMusicByName(int page, int size, String name) {
        page=CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        long offest=(page-1)*size;

        // 创建query对象
        HighlightQuery query = new SimpleHighlightQuery(new SimpleStringCriteria("name:"+name));

// 配置高亮选项
        HighlightOptions options = new HighlightOptions();
// 高亮域
        options.addField("name");
// 前缀和后缀
        options.setSimplePrefix("<span style=\"color:#0071E0;\">");
        options.setSimplePostfix("</span>");

// 设置高亮设置
        query.setHighlightOptions(options);

        // 分页
        query.setOffset(offest);
        query.setRows(size);

        //  执行查询 高亮
        HighlightPage<MusicInfo> highlightPage  = solrTemplate.queryForHighlightPage("lk_core",query, MusicInfo.class);
// 获取高亮数据和普通数据
        List<HighlightEntry<MusicInfo>> highlighted = highlightPage.getHighlighted();
// 遍历多个item数据
        for (HighlightEntry<MusicInfo> itemHighlightEntry : highlighted) {
            MusicInfo item = itemHighlightEntry.getEntity();
            // 获取高亮数据
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();
            if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets() != null
                    && highlights.get(0).getSnipplets().size() > 0) {
                //可能存在多个高亮域
                HighlightEntry.Highlight highlight = highlights.get(0);
                // 获取分片数据
                List<String> snipplets = highlight.getSnipplets();
                String data = snipplets.get(0);
                //替换高亮数据
                item.setName(data);
            }
        }

        Map<String, Object> res = new HashMap<>();

        res.put("list", highlightPage.getContent());
        res.put("totalPage", highlightPage.getTotalPages());
        res.put("currentPage", page);

//        Query query = new SimpleQuery("name:"+name);
//        // 指定偏移量，从0开始
//        query.setOffset(offest);
//        // 查询的size数量
//        query.setRows(size);
//        ScoredPage<MusicInfo> ans = solrTemplate.queryForPage("lk_core", query, MusicInfo.class);
        // 文档数量
//        long totalDocNum = ans.getTotalElements();
//        List<MusicInfo> docList = ans.getContent();
//        HashMap<String,Object> res = new HashMap<>();
//        res.put("list",docList);
//        res.put("maxPage",ans.getTotalPages());
//        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult doSearchMusicByLyric(int page, int size, String lyric) {
        page=CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        long offest=(page-1)*size;

        // 创建query对象
        HighlightQuery query = new SimpleHighlightQuery(new SimpleStringCriteria("lyric:"+lyric));

// 配置高亮选项
        HighlightOptions options = new HighlightOptions();
// 高亮域
        options.addField("lyric");
// 前缀和后缀
        options.setSimplePrefix("<span style=\"color:#0071E0;\">");
        options.setSimplePostfix("</span>");
//结果长度
        options.setFragsize(1024);
// 设置高亮设置
        query.setHighlightOptions(options);

        // 分页
        query.setOffset(offest);
        query.setRows(size);
        //  执行查询 高亮
        HighlightPage<MusicInfo> highlightPage  = solrTemplate.queryForHighlightPage("lk_core",query, MusicInfo.class);
// 获取高亮数据和普通数据
        List<HighlightEntry<MusicInfo>> highlighted = highlightPage.getHighlighted();
// 遍历多个item数据
        for (HighlightEntry<MusicInfo> itemHighlightEntry : highlighted) {
            MusicInfo item = itemHighlightEntry.getEntity();
            // 获取高亮数据
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();

            if (highlights != null && highlights.size() > 0 && highlights.get(0).getSnipplets() != null
                    && highlights.get(0).getSnipplets().size() > 0) {
                //可能存在多个高亮域
//                HighlightEntry.Highlight highlight = highlights.get(0);
                // 获取分片数据
//                List<String> snipplets = highlight.getSnipplets();
//                String data = snipplets.get(0);
                String data ="";
                for (HighlightEntry.Highlight highlight : highlights) {
                    // 获取分片数据
                    List<String> snipplets = highlight.getSnipplets();
                    for (String snipplet : snipplets) {
                        data+=snipplet;
                    }
                }
                //替换高亮数据
                item.setLyric(data);
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("list", highlightPage.getContent());
        res.put("totalPage", highlightPage.getTotalPages());
        res.put("currentPage", page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult updateMusicTop(String musicId) {
//        int res = musicDao.updateMusicAddTop(musicId);
        User user = userService.checkUser();
        if (user == null||(!user.getRoleId().equals("1")&&!user.equals("4"))) {
            return ResponseResult.FAILED("账号未登录或账号权限不足");
        }
        Music music = musicDao.selectById(musicId);
        if (music == null) {
           return ResponseResult.FAILED("音乐不存在");
        }else {
            music.setState(3+"");
            music.setUserId(user.getId());
            music.setUpdateTime(new Date());
            QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<>();
            musicQueryWrapper.eq("id",musicId);
            int update = musicDao.update(music, musicQueryWrapper);
            return update>0?ResponseResult.SUCCESS("音乐顶置成功"):ResponseResult.FAILED("音乐顶置失败");
        }

    }

    @Override
    public ResponseResult getDeleteList(int page, int size) {
        page=CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);

        Page<MusicUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page, size);
        musicAndSingerVoPage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<MusicUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        musicAndSingerVoQueryWrapper.eq("tb_music.`state`",Constants.Music.STATE_DELETE);
        IPage<MusicUpdateInfoToUserVo> musicList = musicDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        log.info(musicList.toString());
//        log.info(offset+"");
//        log.info(size+"");
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult getListBySingId(String musicianId, int page, int size) {
        page=CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        Page<MusicUpdateInfoToUserVo> musicAndSingerVoPage = new Page<>(page, size);
        QueryWrapper<MusicUpdateInfoToUserVo> musicUpdateInfoToUserVoQueryWrapper = null;
        if (!user.getRoleId().equals(Constants.User.ROLE_ADMIN_SUPER_ID)||
            !user.getRoleId().equals(Constants.User.ROLE_ADMIN_MUSIC_ID)){
            musicUpdateInfoToUserVoQueryWrapper =new QueryWrapper<>();
            musicUpdateInfoToUserVoQueryWrapper.eq("tb_music.`state`",Constants.Music.STATE_PUBLISH)
                    .or()
                    .eq("tb_music.`state`",Constants.Music.STATE_TOP);
        }
        musicAndSingerVoPage.addOrder(OrderItem.desc("tb_music.create_time"));
        QueryWrapper<MusicUpdateInfoToUserVo> musicAndSingerVoQueryWrapper = new QueryWrapper<>();
        musicAndSingerVoQueryWrapper.like("tb_music.`singer_id`",musicianId);
        IPage<MusicUpdateInfoToUserVo> musicList = musicDao.getMusicListByPage(musicAndSingerVoPage, musicAndSingerVoQueryWrapper);
        log.info(musicList.toString());
//        log.info(offset+"");
//        log.info(size+"");
        HashMap<String, Object> res = new HashMap<>();
        res.put("list",musicList.getRecords());
        res.put("maxPage",musicList.getPages());
        res.put("currentPage",page);
        return ResponseResult.SUCCESS("获取成功").setData(res);
    }

    @Override
    public ResponseResult getListMusic(int page, int size, String state) {
        page = CheckUtils.checkPage(page);
        size = CheckUtils.checkSize(size);
        Page<MusicItem> musicItemPage = new Page<>(page, size);
        musicItemPage.addOrder(OrderItem.desc("tb_music.create_time"));
        QueryWrapper<MusicItem> musicItemQueryWrapper = new QueryWrapper<>();
        musicItemQueryWrapper.eq("tb_music.`state`",state);
        IPage<MusicItem> res = musicDao.getMusicList(musicItemPage, musicItemQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",res.getRecords());
        map.put("maxpage",res.getPages());
        map.put("currentPage",page);
        map.put("hasMore",res.getPages()>page);
        return ResponseResult.SUCCESS("获取推荐成功").setData(map);
    }

    @Override
    public ResponseResult getMusicListByMusicianId(String musicianId, int page, int size) {
        page = CheckUtils.checkPage(page);
        size = CheckUtils.checkSize(size);
        Page<MusicItem> musicItemPage = new Page<>(page, size);
        musicItemPage.addOrder(OrderItem.desc("tb_music.create_time"));
        QueryWrapper<MusicItem> musicItemQueryWrapper = new QueryWrapper<>();
        musicItemQueryWrapper.ne("tb_music.`state`","0");
        musicItemQueryWrapper.like("tb_music.singer_id",musicianId);
        IPage<MusicItem> res = musicDao.getMusicList(musicItemPage, musicItemQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",res.getRecords());
        map.put("maxpage",res.getPages());
        map.put("currentPage",page);
        map.put("hasMore",res.getPages()>page);
        return ResponseResult.SUCCESS("获取成功").setData(map);
    }

    @Override
    public ResponseResult uploadMusics(MultipartFile[] files) {
        User user = userService.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        String md5 = null;
        InputStream inputStream = null;
        log.info(files.length+"==size");
        ArrayList<String> failedFiles = new ArrayList<>();
        ArrayList<String> successFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            log.info(file.getContentType());
            md5 = null;
            inputStream = null;
            String contentType = file.getContentType();
            if (TextUtils.isEmpty(contentType)) {
                return ResponseResult.FAILED("音频文件格式错误");
            }
            //        获取相关数据，比如说文件类型，文件名称
            String originalFilename = file.getOriginalFilename();
            String type = getType(contentType, originalFilename);
            String fileName = file.getName();
            try{
                inputStream = file.getInputStream();
                md5 = DigestUtils.md5DigestAsHex(inputStream);
                QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<>();
                if (type == null){
                    failedFiles.add(originalFilename);
                    continue;
                }else {
                    if (type.equals(Constants.MusicType.TYPE_MP3)){
                        musicQueryWrapper.eq("md5",md5);
                    }else {
                        musicQueryWrapper.eq("file_high_md5",md5);
                    }
                    Music music = musicDao.selectOne(musicQueryWrapper);
                    if (music != null) {
                        failedFiles.add(originalFilename);
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                failedFiles.add(originalFilename);
                continue;
            }finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //判断文件类型
            log.info("originalFilename ="+originalFilename+
                    "\n type ="+type+
                    "\n contentType ="+contentType);
            if (type==null){
                failedFiles.add(originalFilename);
            }
            log.info("name==" + fileName);
            log.info("originalFilename==" + originalFilename);
            //限制文件大小
            long size = file.getSize();
            if (size>maxSize){
                failedFiles.add(originalFilename);
            }
            log.info(originalFilename);
            //创建保存目录
            //规则：配置目录/日期/类型/ID.类型
            long currentMillions = System.currentTimeMillis();
            String currentDay = simpleDateFormat.format(currentMillions);
            log.info("current day = >" + currentDay);
            String dayPath = musicPath + File.separator + currentDay;
            File dayPathFile = new File(dayPath);
            //判断日期文件夹是否存在
            if (!dayPathFile.exists()){
                dayPathFile.getParentFile().mkdirs();
            }
            String targetName = String.valueOf(idWorker.nextId());
            String targetPath = dayPath+
                    File.separator+type+File.separator+ targetName +"."+type;
            File targetFile = new File(targetPath);
            //判断类型文件夹是否存在
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            //保存文件
            try {
                file.transferTo(targetFile);
                //保存记录到数据库
                String resultPath = currentMillions + "_" + targetName + "." + type;
                Music music = new Music();
                music.setId(targetName);
                music.setContentType(type);
                if (type.equals(Constants.MusicType.TYPE_FLAC)){
                    music.setFileHighPath(targetFile.getPath());
                    music.setFileHighMd5(md5);
                    music.setFileHighUrl(resultPath);
                }else {
                    music.setPath(targetFile.getPath());
                    music.setUrl(resultPath);
                    music.setMd5(md5);
                }
                music.setState("4");
                music.setUserId(user.getId());
                File audioFile = null;
                if (type.equals(Constants.MusicType.TYPE_MP3)){
                    audioFile = new File(music.getPath());
                }else {
                    audioFile = new File(music.getFileHighPath());
                }
                Float mp3Duration = AudioUtil.getMp3Duration(audioFile);
                music.setDuration(mp3Duration+"");

                log.info(originalFilename);
                String[] split = originalFilename.split("\\.");
                String[] nameSplit = split[0].split("-");
                QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
                singerQueryWrapper.eq("name",nameSplit[1]);
                Singer singer = singerDao.selectOne(singerQueryWrapper);
                String singerId = null;
                if (singer == null){
                    singerId = idWorker.nextId()+"";
                    Singer newSinger = new Singer();
                    newSinger.setId(singerId);
                    newSinger.setName(nameSplit[0]);
                    newSinger.setSex(4);
                    newSinger.setPicId(Constants.User.DEFAULT_AVATAR);
                    newSinger.setMusicCount("-1");
                    newSinger.setUserId(user.getId());
                    newSinger.setState("1");
                    singerDao.insert(newSinger);
                }else {
                    singerId = singer.getId();
                }
                music.setName(nameSplit[0]);
                music.setSingerId(singerId);
                music.setSingerName(nameSplit[1]);
                musicDao.insert(music);
                successFiles.add(originalFilename);
            } catch (IOException e) {
                e.printStackTrace();
                failedFiles.add(originalFilename);
            }
        }
        HashMap<String,Object> resMap = new HashMap<>();
        resMap.put("success_list",successFiles);
        resMap.put("fail_list",failedFiles);
        return ResponseResult.SUCCESS("上传结束").setData(resMap);
    }

    private String getType(String contentType,String name){
        String type = null;
        if (Constants.MusicType.TYPE_MP3_WITH_PREFIX_AUDIO.equals(contentType)
                ||name.endsWith(Constants.MusicType.TYPE_MP3)) {
            type = Constants.MusicType.TYPE_MP3;
        }else if (Constants.MusicType.TYPE_FLAC_WITH_PREFIX_AUDIO.equals(contentType)
                ||name.endsWith(Constants.MusicType.TYPE_FLAC)){
            type = Constants.MusicType.TYPE_FLAC;
        }
        return type;
    }
}
