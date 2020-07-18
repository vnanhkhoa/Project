package com.vnak.sotaysinhvien.model;

import java.io.Serializable;

public class Point implements Serializable {
    private String diemCC;
    private String diemGK;
    private String diemCK;
    private String diemTB;
    private String diemChu;

    public Point() {
    }

    public Point(String diemCC, String diemGK, String diemCK, String diemTB, String diemChu) {
        this.diemCC = diemCC;
        this.diemGK = diemGK;
        this.diemCK = diemCK;
        this.diemTB = diemTB;
        this.diemChu = diemChu;
    }

    public String getDiemCC() {
        return diemCC;
    }

    public void setDiemCC(String diemCC) {
        this.diemCC = diemCC;
    }

    public String getDiemGK() {
        return diemGK;
    }

    public void setDiemGK(String diemGK) {
        this.diemGK = diemGK;
    }

    public String getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(String diemCK) {
        this.diemCK = diemCK;
    }

    public String getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(String diemTB) {
        this.diemTB = diemTB;
    }

    public String getDiemChu() {
        return diemChu;
    }

    public void setDiemChu(String diemChu) {
        this.diemChu = diemChu;
    }
}
