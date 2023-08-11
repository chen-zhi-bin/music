package com.chen.music.controller.portal;

import com.chen.music.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/music")
public class MusicApi {
    @Autowired
    private IMusicService iMusicService;

    @GetMapping("/{musicId}")
    public void getMusic(HttpServletResponse response, @PathVariable("musicId") String musicId) {
        try {
            iMusicService.viewMusic(response,musicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
