package com.chen.music.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MusicAndSingerVo implements Serializable {
    private String id;
    private String url;
    private String fileHighUrl;
    private String singrName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSingrName() {
        return singrName;
    }

    public void setSingrName(String singrName) {
        this.singrName = singrName;
    }


}
