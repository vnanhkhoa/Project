package com.sict.drink.model;

import java.io.Serializable;

public class Quan implements Serializable {
    private String id;
    private String ten;
    private String anh;
    private String giomocua;
    private String giatb;
    private String diaChi;


    public Quan() {
    }

    public Quan(String id, String ten, String anh, String giomocua, String giatb, String diaChi) {
        this.id = id;
        this.ten = ten;
        this.anh = anh;
        this.giomocua = giomocua;
        this.giatb = giatb;
        this.diaChi = diaChi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiatb() {
        return giatb;
    }

    public void setGiatb(String giatb) {
        this.giatb = giatb;
    }
}
