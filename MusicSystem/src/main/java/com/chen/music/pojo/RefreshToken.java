package com.chen.music.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
@TableName("tb_refresh_token")
@ApiModel(value = "Refresh_token对象", description = "")
public class RefreshToken implements Serializable {


    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "刷新的token")
    private String refreshToken;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "token的key")
    private String tokenKey;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
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

    @Override
    public String toString() {
        return "Refresh_token{" +
        "id=" + id +
        ", refresh_token=" + refreshToken +
        ", user_id=" + userId +
        ", token_key=" + tokenKey +
        ", create_time=" + createTime +
        ", update_time=" + updateTime +
        "}";
    }
}
