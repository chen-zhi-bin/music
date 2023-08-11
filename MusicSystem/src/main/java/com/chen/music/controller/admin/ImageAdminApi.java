package com.chen.music.controller.admin;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/admin/image")
public class ImageAdminApi {
    @Autowired
    private IImagesService imageService;

    /**
     * 图片（文件）上传
     * 一般来说，现在比较常用的是对象存储--》很简单，看文档就可以学会
     * 使用 nginx + fastDFS --》处理文件上传，   nginx --》负责处理文件访问
     *
     * @param file
     * @return
     */
    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return imageService.deleteImage(imageId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PutMapping("/recover/{imageId}")
    public ResponseResult recoverImage(@PathVariable("imageId") String imageId) {
        return imageService.recoverImage(imageId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PostMapping("/list/{page}/{size}")
    public ResponseResult listImages(@PathVariable("page") int page, @PathVariable("size") int size) {
        return imageService.listImages(page,size);
    }

    @GetMapping("/{imageId}")
    public void getImage(HttpServletResponse response, @PathVariable("imageId") String imageId) {
        try {
            imageService.viewImage(response,imageId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
