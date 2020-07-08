package com.vnak.sotaysinhvien.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String msv;
    private String name;
    private String email;
    private String birth;
    private String gender;
    private String lop;
    private String sdt;
    private String address;
    private String error;

    public User() {
    }

    public User(String id, String msv, String name, String email, String birth, String gender, String lop, String sdt, String address) {
        this.id = id;
        this.msv = msv;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.lop = lop;
        this.sdt = sdt;
        this.address = address;
    }

    public User(String error) {
        this.error = error;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
