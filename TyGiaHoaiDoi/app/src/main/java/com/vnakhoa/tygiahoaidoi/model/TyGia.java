package com.vnakhoa.tygiahoaidoi.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class TyGia implements Serializable {
    private String type;
    private String imageurl;
    private Bitmap bitmap;
    private String muatienamt;
    private String muack;
    private String bantienmat;
    private String banck;

    public TyGia() {
    }

    public TyGia(String type, String imageurl, Bitmap bitmap, String muatienamt, String muack, String bantienmat, String banck) {
        this.type = type;
        this.imageurl = imageurl;
        this.bitmap = bitmap;
        this.muatienamt = muatienamt;
        this.muack = muack;
        this.bantienmat = bantienmat;
        this.banck = banck;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getMuatienamt() {
        return muatienamt;
    }

    public void setMuatienamt(String muatienamt) {
        this.muatienamt = muatienamt;
    }

    public String getMuack() {
        return muack;
    }

    public void setMuack(String muack) {
        this.muack = muack;
    }

    public String getBantienmat() {
        return bantienmat;
    }

    public void setBantienmat(String bantienmat) {
        this.bantienmat = bantienmat;
    }

    public String getBanck() {
        return banck;
    }

    public void setBanck(String banck) {
        this.banck = banck;
    }
}
