package com.vnakhoa.luxury.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private String id;
    private String tenPhong;
    private String iddm;

    public Phong() {
    }

    public Phong(String id, String tenPhong, String iddm) {
        this.id = id;
        this.tenPhong = tenPhong;
        this.iddm = iddm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getIddm() {
        return iddm;
    }

    public void setIddm(String iddm) {
        this.iddm = iddm;
    }
}
