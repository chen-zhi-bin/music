package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageApi {
    @Autowired
    private IImagesService imageService;

    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
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
