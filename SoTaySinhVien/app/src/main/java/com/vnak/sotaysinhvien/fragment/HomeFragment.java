package com.vnak.sotaysinhvien.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vnak.sotaysinhvien.MainActivity;
import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.TableMainLayout;
import com.vnak.sotaysinhvien.Task.LichHocTask;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.SampleObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    LinearLayout calendar;
    public String[] headers = {
            " \t \t \t \t \t \t \t \t \t \t \t \t \t \n\n",
            "\t\t Thứ 2 \t\t",
            "\t\t Thứ 3 \t\t",
            "\t\t Thứ 4 \t\t",
            "\t\t Thứ 5 \t\t",
            "\t\t Thứ 6 \t\t",
            "\t\t Thứ 7 \t\t",
            "\t\t Chủ Nhật \t\t",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       calendar = view.findViewById(R.id.calendar);
       SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
       if (preferences != null) {
           String msv = preferences.getString("MSV",null);
           if (msv != null) {
               LH lh = new LH();
               lh.execute(msv);
           }
       }
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    static class TableLH extends TableMainLayout {


        public static ArrayList<CalendarSubject> dsSubject = new ArrayList<>();

        public TableLH(Context context, String[] headers) {
            super(context, headers);
        }
        
        @Override
        public List<SampleObject> sampleObjects() {
            System.out.println("LIST "+dsSubject.size());
            List<SampleObject> sampleObjects = new ArrayList<>();
            for(CalendarSubject calendarSubject : this.dsSubject ){
                String t2=null,t3=null,t4=null,t5=null,t6=null,t7=null,cn=null;
                for (CalendarST calendarST : calendarSubject.getList_lich_hoc()) {
                    switch (calendarST.getThoigian()){
                        case "T2" :
                            t2 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "T3" :
                            t3 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "T4" :
                            t4 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "T5" :
                            t5 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "T6" :
                            t6 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "T7" :
                            t7 = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                        case "CN" :
                            cn = calendarST.getTiet()+"\n"+calendarST.getPhong();
                            break;
                    }
                }
                SampleObject sampleObject = new SampleObject(
                        calendarSubject.getMonhoc(),
                        "\n"+t2 + "\n",
                        t3,
                        t4,
                        t5,
                        t6,
                        t7,
                        cn
                );
                sampleObjects.add(sampleObject);
        }
            return sampleObjects;
        }
    }
    class LH extends LichHocTask {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Doing something, please wait.");
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(ArrayList<CalendarSubject> calendarSubjects) {
            super.onPostExecute(calendarSubjects);
            TableLH.dsSubject.clear();
            for (CalendarSubject calendarSubject : calendarSubjects) {
                TableLH.dsSubject.add(calendarSubject);
            }
            TableLH tableLH = new TableLH(getContext(),headers);
            calendar.addView(tableLH);
            progressDialog.dismiss();
        }
    }

}