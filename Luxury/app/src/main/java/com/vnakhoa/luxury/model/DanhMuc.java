package com.vnakhoa.luxury.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhMuc implements Serializable {

    private String id;
    private String tenDM;
    private String donGia;
    private ArrayList<Phong> dsPhong;
    private String urlImg;
    private Bitmap imgPhong;
    private String noiDung;

    public DanhMuc() {
    }

    public DanhMuc(String id, String tenDM, String donGia, ArrayList<Phong> dsPhong, String urlImg, Bitmap imgPhong, String noiDung) {
        this.id = id;
        this.tenDM = tenDM;
        this.donGia = donGia;
        this.dsPhong = dsPhong;
        this.urlImg = urlImg;
        this.imgPhong = imgPhong;
        this.noiDung = noiDung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public ArrayList<Phong> getDsPhong() {
        return dsPhong;
    }

    public void setDsPhong(ArrayList<Phong> dsPhong) {
        this.dsPhong = dsPhong;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Bitmap getImgPhong() {
        return imgPhong;
    }

    public void setImgPhong(Bitmap imgPhong) {
        this.imgPhong = imgPhong;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
