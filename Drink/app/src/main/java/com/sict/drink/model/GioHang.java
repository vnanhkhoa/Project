package com.sict.drink.model;

import java.io.Serializable;

public class GioHang implements Serializable {
    private DoUong doUong;
    private int soLuong;

    public GioHang() {
    }

    public GioHang(DoUong doUong, int soLuong) {
        this.doUong = doUong;
        this.soLuong = soLuong;
    }

    public DoUong getDoUong() {
        return doUong;
    }

    public void setDoUong(DoUong doUong) {
        this.doUong = doUong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "doUong=" + doUong +
                ", soLuong=" + soLuong +
                '}';
    }
}
