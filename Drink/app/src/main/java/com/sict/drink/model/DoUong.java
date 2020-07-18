package com.sict.drink.model;

import java.io.Serializable;

public class DoUong implements Serializable {
    private String ten;
    private String gia;
    private String anh;

    public DoUong() {
    }

    public DoUong(String ten, String gia, String anh) {
        this.ten = ten;
        this.gia = gia;
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    @Override
    public String toString() {
        return "DoUong{" +
                "ten='" + ten + '\'' +
                ", gia='" + gia + '\'' +
                ", anh='" + anh + '\'' +
                '}';
    }
}
