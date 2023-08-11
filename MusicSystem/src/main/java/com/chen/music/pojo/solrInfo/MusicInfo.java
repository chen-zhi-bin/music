package com.chen.music.pojo.solrInfo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.util.Date;

@SolrDocument(collection = "lk_core")
public class MusicInfo implements Serializable {


    private String id;

    private String singerId;


    private String name;

    private String picId;

    private String lyric;

    private String url;

    private String state;

    private String path;


    private String fileHighUrl;

    private String singerName;

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getFileHighPath() {
        return fileHighPath;
    }

    public void setFileHighPath(String fileHighPath) {
        this.fileHighPath = fileHighPath;
    }

    private String fileHighPath;

    private String fileHighMd5;

    private String count;

    private String duration;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileHighUrl() {
        return fileHighUrl;
    }

    public void setFileHighUrl(String fileHighUrl) {
        this.fileHighUrl = fileHighUrl;
    }

    public String getFileHighMd5() {
        return fileHighMd5;
    }

    public void setFileHighMd5(String fileHighMd5) {
        this.fileHighMd5 = fileHighMd5;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "id='" + id + '\'' +
                ", singerId='" + singerId + '\'' +
                ", name='" + name + '\'' +
                ", picId='" + picId + '\'' +
                ", lyric='" + lyric + '\'' +
                ", url='" + url + '\'' +
                ", state='" + state + '\'' +
                ", path='" + path + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                ", singerName='" + singerName + '\'' +
                ", fileHighPath='" + fileHighPath + '\'' +
                ", fileHighMd5='" + fileHighMd5 + '\'' +
                ", count='" + count + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
