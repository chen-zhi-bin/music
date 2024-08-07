package com.chen.music.pojo.vo;

public class CollectionVo {

    private String musicId;
    private String name;
    private String singerName;
    private String url;
    private String fileHighUrl;
    private String picId;
    private String duration;

    @Override
    public String toString() {
        return "CollectionVo{" +
                "musicId='" + musicId + '\'' +
                ", name='" + name + '\'' +
                ", singerName='" + singerName + '\'' +
                ", url='" + url + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                ", picId='" + picId + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }


    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
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
}
