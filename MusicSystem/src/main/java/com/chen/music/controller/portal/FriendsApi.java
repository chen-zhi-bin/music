package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.IFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend_link")
public class FriendsApi {

    @Autowired
    private IFriendsService friendLinkService;

    @GetMapping
    public ResponseResult getLinks(){
        return friendLinkService.getList();
    }
}
