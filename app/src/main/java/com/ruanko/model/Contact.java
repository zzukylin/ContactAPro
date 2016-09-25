package com.ruanko.model;

/**
 * Created by zzuitachi on 2016/5/8.
 */
public class Contact {
    private String number=null;
    private String name=null;
    private String phone=null;
    private String email=null;
    private String address=null;
    private String gender=null;
    private String relationship=null;
    private String remark=null;
    private int id;
    public Contact(){
        number="";
        name="";
        phone="";
        email="";
        address="";
        gender="";
        relationship="";
        remark="";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
