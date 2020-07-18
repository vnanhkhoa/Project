package com.sict.drink.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class HoaDon implements Serializable {
    private String ten;
    private String sdt;
    private String diaChi;
    private ArrayList<GioHang> hoaDon;
    private long sum;

    public HoaDon() {
    }

    public HoaDon(String ten, String sdt, String diaChi, ArrayList<GioHang> hoaDon,long sum) {
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.hoaDon = hoaDon;
        this.sum = sum;
    }
    @Exclude
    public Map<String,Object> toMap () {
        HashMap<String,Object> result = new HashMap<>();
        try {
            result.put("ten",this.ten);
            result.put("sdt",this.sdt);
            result.put("diachi",this.diaChi);
//            HashMap<Integer,Object> cthd = new HashMap<>();
//            int i = 0;
//            for (GioHang gioHang : this.hoaDon) {
//                cthd.put(i,gioHang);
//                i++;
//            }
            result.put("chitiethoadon",this.hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
