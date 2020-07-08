package com.vnak.sotaysinhvien.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarSubject implements Serializable {
    private String monhoc;
    private ArrayList<CalendarST> list_lich_hoc;

    public CalendarSubject() {
    }

    public CalendarSubject(String monhoc, ArrayList<CalendarST> list_lich_hoc) {
        this.monhoc = monhoc;
        this.list_lich_hoc = list_lich_hoc;
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
}
