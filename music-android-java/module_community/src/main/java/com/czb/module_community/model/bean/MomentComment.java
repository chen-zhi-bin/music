package com.czb.module_community.model.bean;

public class MomentComment {
    private String songId;
    private String comment;

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

    @Override
    public String toString() {
        return "MomentComment{" +
                "musicId='" + songId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
