package com.chen.music.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("tb_collection")
public class Collection {
    private String id;
    private String userId;
    private String musicId;

    @Override
    public String toString() {
        return "Collection{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", musicId='" + musicId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}
