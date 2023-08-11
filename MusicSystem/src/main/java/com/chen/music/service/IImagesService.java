package com.chen.music.service;

import com.chen.music.pojo.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.music.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
public interface IImagesService extends IService<Image> {

    ResponseResult uploadImage(MultipartFile file);

    ResponseResult deleteImage(String imageId);

    ResponseResult listImages(int page, int size);

    void viewImage(HttpServletResponse response, String imageId)throws IOException;

    ResponseResult recoverImage(String imageId);
}
