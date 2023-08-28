package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.music.pojo.Image;
import com.chen.music.mapper.ImageDao;
import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.service.IUserService;
import com.chen.music.utils.CheckUtils;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ImagesServiceImpl extends ServiceImpl<ImageDao, Image> implements IImagesService {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    @Value("${music.system.image.save-path-img}")
    public String imagePath;

    @Value("${music.system.image.max-size}")
    public long maxSize;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IUserService userService;

    @Autowired
    private ImageDao imageDao;

    @Override
    public ResponseResult uploadImage(MultipartFile file) {
        String md5 = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            md5= DigestUtils.md5DigestAsHex(inputStream);
            QueryWrapper<Image> imagesQueryWrapper = new QueryWrapper<>();
            imagesQueryWrapper.eq("md5",md5);
            Image image = imageDao.selectOne(imagesQueryWrapper);
            if (image != null) {
                Map<String,Object> res= new HashMap<>();
                res.put("name",image.getName());
                res.put("id",image.getUrl());
                return ResponseResult.SUCCESS("图片已经存在").setData(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("图片上传失败");
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
//        判断文件类型，只支持图片，比如说：png，jpg，gif
        String contentType = file.getContentType();
        if (TextUtils.isEmpty(contentType)) {
            return ResponseResult.FAILED("图片文件格式错误");
        }
        //        获取相关数据，比如说文件类型，文件名称
        String originalFilename = file.getOriginalFilename();
        String type = getType(contentType, originalFilename);
        if (type==null){
            return ResponseResult.FAILED("不支持此文件类型");
        }
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
        String dayPath = imagePath + File.separator + currentDay;
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

            User sobUser = userService.checkUser();

            if (sobUser == null) {
                return ResponseResult.ACCOUNT_NOT_LOGIN();
            }
            Image image = new Image();
            image.setContentType(contentType);
            image.setId(targetName);
            image.setPath(targetFile.getPath());
            image.setName(originalFilename);
            image.setUrl(resultPath);
            image.setState("1");
            image.setUserId(sobUser.getId());
            image.setMd5(md5);
            //记录文件
            //保存记录的数据

            imageDao.insert(image);
            //返回结果
            return ResponseResult.SUCCESS("上传成功").setData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        记录文件
//        返回结果
        return ResponseResult.FAILED("上传失败，请稍后重试");
    }

    @Override
    public ResponseResult deleteImage(String imageId) {
        Image image = imageDao.selectById(imageId);
        if (image == null) {
            return ResponseResult.FAILED("删除失败，图片不存在");
        }
        image.setState("0");
        image.setUpdateTime(new Date());
        int i = imageDao.updateById(image);
        return i>0?ResponseResult.SUCCESS("删除成功"):ResponseResult.FAILED("删除失败，图片不存在");
//        int result = imageDao.deleteByImageId(imageId);
//        if (result>0){
//            return ResponseResult.SUCCESS("删除成功");
//        }
//        return ResponseResult.FAILED("删除失败，图片不存在");
    }

    @Override
    public ResponseResult listImages(int page, int size) {
        //处理page,size

        page= CheckUtils.checkPage(page);
        size=CheckUtils.checkSize(size);
//        SobUser sobUser = userService.checkSobUser();
//        if (sobUser == null) {
//            return ResponseResult.ACCOUNT_NOT_LOGIN();
//        }
        Page<Image> imagePage = new Page<>(page, size);
        imagePage.addOrder(OrderItem.desc("create_time"));
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
//        imageQueryWrapper.ne("state","0");
        Page<Image> selectPage = imageDao.selectPage(imagePage, imageQueryWrapper);
        List<Image> imageList = selectPage.getRecords();
        HashMap<String, Object> res = new HashMap<>();
        res.put("imageList",imageList);
        res.put("currentPage",selectPage.getCurrent());
        res.put("maxPage",selectPage.getPages());
        //返回结果
        return ResponseResult.SUCCESS("获取列表成功").setData(res);
    }

    @Override
    public void viewImage(HttpServletResponse response, String imageId) throws IOException{
//配置的目录已知
        //根据尺寸来动态返回图片给前端
        //好处：减少带宽，传输速度快
//        缺点：消耗后台cpu资源
//        推荐做法：上传来的时候，把图片复制成三个尺寸：大，中，小
//        根据尺寸范围，返回结果
        //需要日期
        String[] paths = imageId.split("_");
        String dayValue = paths[0];
        String format = simpleDateFormat.format(Long.parseLong(dayValue));
//        ID
        String name = paths[1];
//        需要类型
//        String type = name.substring(name.length() - 3);
//        String type = getType(,name);
        String[] nameSplit = name.split("\\.");
        String type = nameSplit[1];
//        使用日期的时间戳_ID.类型
        String targetPath = imagePath + File.separator + format + File.separator +
                type+
                File.separator +name;
        log.info("get image target path ==> "+targetPath);
//        File file = new File(imagePath + File.separator + "e.png");
        File file = new File(targetPath);
        OutputStream writer = null;
        FileInputStream fos = null;
        try {
            response.setContentType("image/png");
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
    }

    @Override
    public ResponseResult recoverImage(String imageId) {
        Image image = imageDao.selectById(imageId);
        if (image == null) {
            return ResponseResult.FAILED("图片不存在");
        }
        image.setState("1");
        int res = imageDao.updateById(image);
        if (res>0) {
            return ResponseResult.SUCCESS("恢复成功");
        }
        return ResponseResult.FAILED("恢复失败");
    }


    private String getType(String contentType, String name) {
//        log.info("contentType ==> "+contentType);
//        log.info("contentType ==> "+name);
        String type =null;
        if (Constants.ImageType.TYPE_GIF_WITH_PREFIX.equals(contentType)
                && name.endsWith(Constants.ImageType.TYPE_GIF)){
            type = Constants.ImageType.TYPE_GIF;
        }else if (Constants.ImageType.TYPE_JPEG_WITH_PREFIX.equals(contentType)
                && (name.endsWith(Constants.ImageType.TYPE_JPEG)||name.endsWith(Constants.ImageType.TYPE_JPG))){
            type = Constants.ImageType.TYPE_JPEG;
        }else if (Constants.ImageType.TYPE_PNG_WITH_PREFIX.equals(contentType)
                && name.endsWith(Constants.ImageType.TYPE_PNG)){
            type = Constants.ImageType.TYPE_PNG;
        }
//        else if (Constants.ImageType.TYPE_JPG_WITH_PREFIX.equals(contentType)
//                &&name.endsWith(Constants.ImageType.TYPE_JPG)){
//            type = Constants.ImageType.TYPE_JPG;
//        }
        return type;
    }
}
