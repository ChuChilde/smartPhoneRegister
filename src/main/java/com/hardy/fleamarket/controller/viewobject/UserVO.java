package com.hardy.fleamarket.controller.viewobject;

import java.util.List;

public class UserVO {

    private String name;

    private String headPicture;

    private String gender;

    private double location;

    private int createTime;

    private Integer creditValue;

    private List comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public Integer getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Integer creditValue) {
        this.creditValue = creditValue;
    }

    public List getComment() {
        return comment;
    }

    public void setComment(List comment) {
        this.comment = comment;
    }
}