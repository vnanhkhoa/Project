package com.vnak.sotaysinhvien.model;

import java.io.Serializable;

public class Work implements Serializable {
    private String gio;
    private String thoiGian;
    private String diaDiem;

    public Work() {
    }

    public Work(String gio, String thoiGian, String diaDiem) {
        this.gio = gio;
        this.thoiGian = thoiGian;
        this.diaDiem = diaDiem;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    @Override
    public String toString() {
        return "Work{" +
                "gio='" + gio + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", diaDiem='" + diaDiem + '\'' +
                '}';
    }
}
