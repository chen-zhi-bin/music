package com.chen.music.pojo.vo;

import java.util.Date;

public class MusicianUpdateInfoToUserVo {
    private String musicianId;
    private String name;
    private String sex;
    private String picId;
    private String introduction;
    private String userId;
    private String userName;
    private String state;
    private Date createTime;
    private Date updateTime;

    public String getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(String musicianId) {
        this.musicianId = musicianId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "MusicianUpdateInfoToUserVo{" +
                "musicianId='" + musicianId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", picId='" + picId + '\'' +
                ", introduction='" + introduction + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
