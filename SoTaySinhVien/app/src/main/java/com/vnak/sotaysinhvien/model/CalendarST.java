package com.vnak.sotaysinhvien.model;

import java.io.Serializable;

public class CalendarST implements Serializable {
    private String phong;
    private String tiet;
    private String thoigian;

    public CalendarST() {
    }

    public CalendarST(String phong, String tiet, String thoigian) {
        this.phong = phong;
        this.tiet = tiet;
        this.thoigian = thoigian;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getTiet() {
        return tiet;
    }

    public void setTiet(String tiet) {
        this.tiet = tiet;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    @Override
    public String toString() {
        return "CalendarST{" +
                "phong='" + phong + '\'' +
                ", tiet='" + tiet + '\'' +
                ", thoigian='" + thoigian + '\'' +
                '}';
    }
}
