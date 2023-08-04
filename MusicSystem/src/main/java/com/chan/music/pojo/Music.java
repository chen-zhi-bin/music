package com.chan.music.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(value = "发行时间")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "图片id")
    private String picId;

    @ApiModelProperty(value = "歌词")
    private String lyric;

    private String url;

    @ApiModelProperty(value = "状态（0为删除，1为可用）")
    private String state;

    private String md5;


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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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

    @Override
    public String toString() {
        return "Music{" +
        "id=" + id +
        ", singer_id=" + singerId +
        ", name=" + name +
        ", introduction=" + introduction +
        ", create_time=" + createTime +
        ", update_time=" + updateTime +
        ", pic_id=" + picId +
        ", lyric=" + lyric +
        ", url=" + url +
        ", state=" + state +
        ", md5=" + md5 +
        "}";
    }
}
