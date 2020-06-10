package com.example.khachsan.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Phong implements Serializable {
    String maPhong;
    String tenPhong;
    String iddm;

    public Phong(String maPhong, String tenPhong, String iddm) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.iddm = iddm;
    }

    public Phong() {
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
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

    @NonNull
    @Override
    public String toString() {
        return "Mã Phong: "+this.maPhong+"\nTên Phòng: "+this.tenPhong+"\nMã Danh Mục: "+this.iddm;
    }
}
