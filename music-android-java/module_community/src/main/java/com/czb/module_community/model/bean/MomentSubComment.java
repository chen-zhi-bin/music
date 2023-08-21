package com.czb.module_community.model.bean;

public class MomentSubComment {
    private String comment;
    private String parentId;
    private String parentUserAvatar;
    private String parentUserId;
    private String parentUserName;
    private String songId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentUserAvatar() {
        return parentUserAvatar;
    }

    public void setParentUserAvatar(String parentUserAvatar) {
        this.parentUserAvatar = parentUserAvatar;
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

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
