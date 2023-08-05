package com.chen.music.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@TableName("tb_singer")
@ApiModel(value = "Singer对象", description = "")
public class Singer implements Serializable {


    private String id;

    @TableField("`name`")
    private String name;

    private Integer sex;

    private String picId;

    private Date birth;

    private String location;

    private String introduction;

    private String musicCount;


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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMusicCount() {
        return musicCount;
    }

    public void setMusicCount(String musicCount) {
        this.musicCount = musicCount;
    }

    @Override
    public String toString() {
        return "Singer{" +
        "id=" + id +
        ", name=" + name +
        ", sex=" + sex +
        ", pic_id=" + picId +
        ", birth=" + birth +
        ", location=" + location +
        ", introduction=" + introduction +
        ", music_count=" + musicCount +
        "}";
    }
}
