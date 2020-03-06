package com.hardy.fleamarket.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class CommentModel {

    private Integer id;

    @JsonIgnore
    private Integer userId;

    private Integer byUserId;

    private String byUserHeadPicture;

    private String byUserName;

    private String content;

    private Integer star;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public String getByUserHeadPicture() {
        return byUserHeadPicture;
    }

    public void setByUserHeadPicture(String byUserHeadPicture) {
        this.byUserHeadPicture = byUserHeadPicture;
    }

    public String getByUserName() {
        return byUserName;
    }

    public void setByUserName(String byUserName) {
        this.byUserName = byUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", byUserId=" + byUserId +
                ", byUserHeadPicture='" + byUserHeadPicture + '\'' +
                ", byUserName='" + byUserName + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", createTime=" + createTime +
                '}';
    }
}