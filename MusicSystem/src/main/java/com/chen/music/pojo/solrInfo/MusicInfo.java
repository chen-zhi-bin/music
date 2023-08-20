package com.chen.music.pojo.solrInfo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.util.Date;

@SolrDocument(collection = "lk_core")
public class MusicInfo implements Serializable {

//这里的命名必须和solr中设置的一样
    private String id;

    private String singer_id;

    private String name;

    private String pic_id;

    private String lyric;

    private String url;

    private String state;

    private String path;

    private String file_high_url;

    private String singer_name;

    private String count;

    private String duration;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getFile_high_url() {
        return file_high_url;
    }

    public void setFile_high_url(String file_high_url) {
        this.file_high_url = file_high_url;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "id='" + id + '\'' +
                ", singer_id='" + singer_id + '\'' +
                ", name='" + name + '\'' +
                ", pic_id='" + pic_id + '\'' +
                ", lyric='" + lyric + '\'' +
                ", url='" + url + '\'' +
                ", state='" + state + '\'' +
                ", path='" + path + '\'' +
                ", file_high_url='" + file_high_url + '\'' +
                ", singer_name='" + singer_name + '\'' +
                ", count='" + count + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
