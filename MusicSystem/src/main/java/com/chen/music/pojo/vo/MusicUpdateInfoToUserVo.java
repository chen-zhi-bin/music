package com.chen.music.pojo.vo;

import java.util.Date;

public class MusicUpdateInfoToUserVo {
    private String musicId;
    private String url;
    private String fileHighUrl;
    private Date createTime;
    private Date updateTime;
    private String singerName;
    private String userId;
    private String userName;
    private String musicName;
    private String picId;
    private String lyric;
    private String state;
    private String musicianId;


    public String getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(String musicianId) {
        this.musicianId = musicianId;
    }

    @Override
    public String toString() {
        return "MusicUpdateInfoToUserVo{" +
                "musicId='" + musicId + '\'' +
                ", url='" + url + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", singerName='" + singerName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", musicName='" + musicName + '\'' +
                ", picId='" + picId + '\'' +
                ", lyric='" + lyric + '\'' +
                ", state='" + state + '\'' +
                ", musicianId='" + musicianId + '\'' +
                '}';
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}
