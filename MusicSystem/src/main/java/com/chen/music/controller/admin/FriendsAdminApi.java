package com.chen.music.controller.admin;

import com.chen.music.pojo.Friends;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/friend_link")
public class FriendsAdminApi {

    @Autowired
    private IFriendsService friendLinkService;

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody Friends friendLink){
        return friendLinkService.addFriendLink(friendLink);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @DeleteMapping("/{friendId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendId")String friendId){
        return friendLinkService.deleteFriendLink(friendId);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId")String friendLinkId,
                                           @RequestBody Friends friendLink){
        return friendLinkService.updateFriendLink(friendLinkId,friendLink);
    }

    @PreAuthorize("@permission.superAdmin()||@permission.imageAdmin()")
    @GetMapping
    public ResponseResult getLinks(){
        return friendLinkService.getList();
    }
}
