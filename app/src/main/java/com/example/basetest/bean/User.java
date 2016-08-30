package com.example.basetest.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Administrator on 2016/8/26.
 */
public class User {
    @DatabaseField(id = true)
    private String userId;
    @DatabaseField
    private String name;
    @DatabaseField
    private String address;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String sex;
    @DatabaseField
    private String age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
