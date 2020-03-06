package com.hardy.fleamarket.entity;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Long phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.head_picture
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private String headPicture;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Integer gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.longitude
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Double longitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.latitude
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Double latitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.create_date
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.credit_value
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    private Integer creditValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public User(Integer id, Long phone, String name, String headPicture, Integer gender, Double longitude, Double latitude, Date createDate, Integer creditValue) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.headPicture = headPicture;
        this.gender = gender;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createDate = createDate;
        this.creditValue = creditValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone
     *
     * @return the value of user.phone
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.head_picture
     *
     * @return the value of user.head_picture
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public String getHeadPicture() {
        return headPicture;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.longitude
     *
     * @return the value of user.longitude
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.latitude
     *
     * @return the value of user.latitude
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.create_date
     *
     * @return the value of user.create_date
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.credit_value
     *
     * @return the value of user.credit_value
     *
     * @mbg.generated Sun Dec 29 17:59:00 CST 2019
     */
    public Integer getCreditValue() {
        return creditValue;
    }
}