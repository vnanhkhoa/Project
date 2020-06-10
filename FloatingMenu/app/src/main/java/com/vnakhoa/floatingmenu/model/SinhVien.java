package com.vnakhoa.floatingmenu.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private int masv;
    private String ten;
    private String lop;
    private int img;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public SinhVien() {
    }

    public SinhVien(int masv, String ten, String lop, int img) {
        this.masv = masv;
        this.ten = ten;
        this.lop = lop;
        this.img = img;
    }

    public int getMasv() {
        return masv;
    }

    public void setMasv(int masv) {
        this.masv = masv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    @NonNull
    @Override
    public String toString() {
        return this.ten+"\n\n\n"+this.lop;
    }
}
