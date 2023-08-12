package com.chen.music.pojo.vo;

import javax.xml.crypto.Data;
import java.util.Date;

public class MusicItem {
    private String musicId;
    private String musicName;
    private String url;
    private String fileHighUrl;
    private Date createTime;
    private String singerName;

    @Override
    public String toString() {
        return "MusicItem{" +
                "musicId='" + musicId + '\'' +
                ", musicName='" + musicName + '\'' +
                ", url='" + url + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                ", createTime=" + createTime +
                ", singerName='" + singerName + '\'' +
                '}';
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileHighUrl() {
        return fileHighUrl;
    }

    public void setFileHighUrl(String fileHighUrl) {
        this.fileHighUrl = fileHighUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
}
