package com.chen.music.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */

@TableName("tb_music")
@ApiModel(value = "Music对象", description = "")
public class Music implements Serializable {


    private String id;

    private String singerId;

    @TableField("`name`")
    private String name;

    private String introduction;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "发行时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "图片id")
    private String picId;

    @ApiModelProperty(value = "歌词")
    private String lyric;

    private String url;

    @ApiModelProperty(value = "状态（0为删除，1为可用）")
    private String state;

    private String md5;

    private String contentType;

    private String path;

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", singerId='" + singerId + '\'' +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", picId='" + picId + '\'' +
                ", lyric='" + lyric + '\'' +
                ", url='" + url + '\'' +
                ", state='" + state + '\'' +
                ", md5='" + md5 + '\'' +
                ", contentType='" + contentType + '\'' +
                ", path='" + path + '\'' +
                ", userId='" + userId + '\'' +
                ", fileHighUrl='" + fileHighUrl + '\'' +
                ", singerName='" + singerName + '\'' +
                ", fileHighPath='" + fileHighPath + '\'' +
                ", fileHighMd5='" + fileHighMd5 + '\'' +
                ", count='" + count + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
