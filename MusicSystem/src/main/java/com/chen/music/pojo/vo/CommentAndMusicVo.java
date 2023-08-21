package com.chen.music.pojo.vo;

public class CommentAndMusicVo {

    String id;
    String avatar;
    String userId;
    String userName;
    String songId;
    String comment;
    String commentTime;
    String musicId;
    String singerName;
    String singerId;
    String musicName;
    String picId;
    String url;
    String duration;
    String contentType;
    String fileHighUrl;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileHighUrl() {
        return fileHighUrl;
    }

    public void setFileHighUrl(String fileHighUrl) {
        this.fileHighUrl = fileHighUrl;
    }

    @Override
    public String toString() {
        return "CommentAndMusicVo{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", songId='" + songId + '\'' +
                ", comment='" + comment + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", musicId='" + musicId + '\'' +
                ", singerName='" + singerName + '\'' +
                ", singerId='" + singerId + '\'' +
                ", musicName='" + musicName + '\'' +
                ", picId='" + picId + '\'' +
                ", url='" + url + '\'' +
                ", duration='" + duration + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                '}';
    }
}
