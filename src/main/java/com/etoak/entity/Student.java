package com.etoak.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Student {
    @XStreamAlias("Id")
    private String id;
    private String name;
    private String address;
    private String email;
    private Birthday day;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(Birthday day) {
        // TODO Auto-generated method stub
        this.day = day;
    }

}
