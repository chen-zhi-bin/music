package com.chen.music.controller.portal;

import com.chen.music.response.ResponseResult;
import com.chen.music.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class CollectionApi {
    @Autowired
    private ICollectionService collectionService;

    @DeleteMapping("/{music_id}")
    public ResponseResult deleteCollection(@PathVariable("music_id")String musicId){
        return collectionService.deleteCollectionById(musicId);
    }

    @PostMapping("/{music_id}")
    public ResponseResult doCollection(@PathVariable("music_id")String musicId){
        return collectionService.doCollection(musicId);
    }

    @GetMapping("/list/{page}")
    public ResponseResult getCollections(@PathVariable("page")int page){
        return collectionService.getCollections(page);
    }
}
