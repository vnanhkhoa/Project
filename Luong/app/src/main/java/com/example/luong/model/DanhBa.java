package com.example.luong.model;

import androidx.annotation.NonNull;

public class DanhBa {
    String name;
    String phone;

    public DanhBa(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public DanhBa() {
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

    @NonNull
    @Override
    public String toString() {
        return this.name+"\n"+this.phone;
    }
}
