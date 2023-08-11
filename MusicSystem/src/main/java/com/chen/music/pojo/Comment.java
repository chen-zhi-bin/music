package com.chen.music.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
@TableName("tb_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {


    @ApiModelProperty(value = "ID")
    private String id;

    private String avatar;

    private String userName;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "歌曲id")
    private String songId;

    @ApiModelProperty(value = "评论内容")
    @TableField("`comment`")
    private String comment;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "状态（0为已删除，1为可可见）")
    private String state;

    private String parentId;

    private String parentUserId;

    private String parentUserName;

    private String parentUserAvatar;

    @TableField(exist=false)
    private HashMap<String,Object> subComments = new HashMap<>();

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", songId='" + songId + '\'' +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", state='" + state + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentUserId='" + parentUserId + '\'' +
                ", parentUserName='" + parentUserName + '\'' +
                ", parentUserAvatar='" + parentUserAvatar + '\'' +
                ", subComments=" + subComments +
                '}';
    }

    public String getParentUserAvatar() {
        return parentUserAvatar;
    }

    public void setParentUserAvatar(String parentUserAvatar) {
        this.parentUserAvatar = parentUserAvatar;
    }

    public HashMap<String,Object> getSubComments() {
        return subComments;
    }

    public void setSubComments(HashMap<String,Object> subComments) {
        this.subComments.clear();
        this.subComments.putAll(subComments);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }


}
