package com.vnak.sotaysinhvien.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarSubject implements Serializable {
    private String monhoc;
    private ArrayList<CalendarST> list_lich_hoc;
    private ArrayList<Point> dsDiem;
    private ArrayList<Work> dsWork;

    public CalendarSubject() {
    }

    public ArrayList<Point> getDsDiem() {
        return dsDiem;
    }

    public void setDsDiem(ArrayList<Point> dsDiem) {
        this.dsDiem = dsDiem;
    }

    public String getMonhoc() {
        return monhoc;
    }

    public ArrayList<CalendarST> getList_lich_hoc() {
        return list_lich_hoc;
    }

    public void setMonhoc(String monhoc) {
        this.monhoc = monhoc;
    }

    public void setList_lich_hoc(ArrayList<CalendarST> list_lich_hoc) {
        this.list_lich_hoc = list_lich_hoc;
    }

    public ArrayList<Work> getDsWork() {
        return dsWork;
    }

    public void setDsWork(ArrayList<Work> dsWork) {
        this.dsWork = dsWork;
    }
}
