package com.hardy.fleamarket.service.model;

import java.util.Date;

/**
 * 这里需求基本和User一样的字段，不过在有的需求，比如User里需要保存一个字段用于业务逻辑时就不需要放到User Model了
 */
public class UserModel {

    private Integer id;

    private Long phone;

    private String name;

    private String headPicture;

    private Integer gender;

    private Double longitude;

    private Double latitude;

    private Date createDate;

    private Integer creditValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Integer creditValue) {
        this.creditValue = creditValue;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", phone=" + phone +
                ", name='" + name + '\'' +
                ", headPicture='" + headPicture + '\'' +
                ", gender=" + gender +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createDate=" + createDate +
                ", creditValue=" + creditValue +
                '}';
    }
}
