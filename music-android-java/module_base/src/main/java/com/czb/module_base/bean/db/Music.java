package com.czb.module_base.bean.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music {
    @NonNull
    @PrimaryKey
    public String id;
    @ColumnInfo(name = "songUrl")
    public String songUrl;
    @ColumnInfo(name = "songName")
    public String songName;
    @ColumnInfo(name = "artist")
    public String artist;
    @ColumnInfo(name = "songCover")
    public String songCover;
    @ColumnInfo(name = "duration")
    public Long duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongCover() {
        return songCover;
    }

    public void setSongCover(String songCover) {
        this.songCover = songCover;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", songName='" + songName + '\'' +
                ", artist='" + artist + '\'' +
                ", songCover='" + songCover + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
