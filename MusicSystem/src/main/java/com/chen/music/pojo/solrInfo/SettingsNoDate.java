package com.chen.music.pojo.solrInfo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("tb_settings")
public class SettingsNoDate implements Serializable {
    private String id;
    private String key;
    private String value;

    public SettingsNoDate(){

    }

    public SettingsNoDate(String id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "HistoryViewCount{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
