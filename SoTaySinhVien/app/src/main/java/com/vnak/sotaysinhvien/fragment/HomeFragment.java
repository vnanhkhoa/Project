package com.vnak.sotaysinhvien.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnak.sotaysinhvien.MainActivity;
import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.Service;
import com.vnak.sotaysinhvien.TableMainLayout;
import com.vnak.sotaysinhvien.Task.LichHocTask;
import com.vnak.sotaysinhvien.ThemLichCNActivity;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.SampleObject;
import com.vnak.sotaysinhvien.model.Work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }
    LinearLayout calendar,calendarWork;
    FloatingActionButton addCV;
    ArrayList<CalendarSubject> listWork;
    public String[] headers ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       calendar = view.findViewById(R.id.calendar);
       calendarWork = view.findViewById(R.id.calendarWork);
       addCV = view.findViewById(R.id.addCV);
       SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
       if (preferences != null) {
           String msv = preferences.getString("MSV",null);
           if (msv != null) {
               String urlservice = new Service().URLSERVER+"api/calendar/"+msv;
               LH lh = new LH();
               lh.execute(urlservice);
           }
       }
        addEvents();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = getContext().getSharedPreferences("Work", Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString("work",null);
            if (json != null) {
                headers = new String[]{
                        " \t\t\t \t \t \t \t \t \t \t \t \t \t \t \t \n\n",
                        "\t\t\t\t\t Thứ 2 \t\t\t\t\t",
                        "\t\t\t\t\t Thứ 3 \t\t\t\t\t",
                        "\t\t\t\t\t Thứ 4 \t\t\t\t\t",
                        "\t\t\t\t\t Thứ 5 \t\t\t\t\t",
                        "\t\t\t\t\t Thứ 6 \t\t\t\t\t",
                        "\t\t\t\t\t Thứ 7 \t\t\t\t\t",
                        "\t\t\t\t\t Chủ Nhật \t\t\t\t\t",
                };
                listWork = gson.fromJson(json, new TypeToken<ArrayList<CalendarSubject>>(){}.getType());
                if (listWork.size() > 0) {
                    calendarWork.addView(new LichCaNhan(getContext(),headers));
                } else {
                    showTextView();
                }
            } else {
                showTextView();
            }
        } else {
            showTextView();
        }

    }

    private void showTextView() {
        TextView textView = new TextView(getContext());
        textView.setText("TRỐNG");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(0,160,0,0);
        calendarWork.addView(textView);
        calendarWork.setOrientation(LinearLayout.VERTICAL);
        calendarWork.setGravity(Gravity.CENTER);
    }


    private void addEvents() {
        addCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThemLichCNActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}

    class LichCaNhan extends TableMainLayout {
        public LichCaNhan(Context context, String[] headers) {
            super(context, headers);
        }

        @Override
        public List<SampleObject> sampleObjects() {
            List<SampleObject> sampleObjects = new ArrayList<>();
            for(CalendarSubject calendarSubject : listWork){
                String t2="",t3="",t4="",t5="",t6="",t7="",cn="";
                for (Work work : calendarSubject.getDsWork()) {
                    switch (work.getThoiGian()){
                        case "T2" :
                            t2 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "T3" :
                            t3 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "T4" :
                            t4 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "T5" :
                            t5 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "T6" :
                            t6 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "T7" :
                            t7 = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                        case "CN" :
                            cn = work.getGio()+"\n"+work.getDiaDiem();
                            break;
                    }
                }
                SampleObject sampleObject = new SampleObject(
                        calendarSubject.getMonhoc(),
                        "\n"+t2 +"\n",
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
                String t2="",t3="",t4="",t5="",t6="",t7="",cn="";
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
            headers = new String[]{
                    " \t \t \t \t \t \t \t \t \t \t \t \t \t \n\n",
                    "\t\t Thứ 2 \t\t",
                    "\t\t Thứ 3 \t\t",
                    "\t\t Thứ 4 \t\t",
                    "\t\t Thứ 5 \t\t",
                    "\t\t Thứ 6 \t\t",
                    "\t\t Thứ 7 \t\t",
                    "\t\t Chủ Nhật \t\t",
            };
            TableLH tableLH = new TableLH(getContext(),headers);
            calendar.addView(tableLH);
            progressDialog.dismiss();
        }
    }

}