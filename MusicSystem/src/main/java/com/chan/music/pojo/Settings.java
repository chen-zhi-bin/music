package com.chan.music.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@TableName("tb_settings")
@ApiModel(value = "Settings对象", description = "")
public class Settings implements Serializable {


    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "键")
    @TableField("`key`")
    private String key;

    @ApiModelProperty(value = "值")
    @TableField("`value`")
    private String value;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


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

    @Override
    public String toString() {
        return "Settings{" +
        "id=" + id +
        ", key=" + key +
        ", value=" + value +
        ", create_time=" + createTime +
        ", update_time=" + updateTime +
        "}";
    }
}
