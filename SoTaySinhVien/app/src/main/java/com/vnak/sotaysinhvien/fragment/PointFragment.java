package com.vnak.sotaysinhvien.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.TableMainLayout;
import com.vnak.sotaysinhvien.Task.PointTask;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.Point;
import com.vnak.sotaysinhvien.model.SampleObject;

import java.util.ArrayList;
import java.util.List;

public class PointFragment extends Fragment {

    LinearLayout tblPoint;
    public String[] headers = {
            " \t \t \t \t \t \t \t \t \t \t \t \t \t \n\n",
            "\t\t Điểm CC \t\t",
            "\t\t Điểm Giữa Kỳ \t\t",
            "\t\t Điểm Cuối Kỳ \t\t",
            "\t\t Điểm TB \t\t",
            "\t\t Điểm Chữ \t\t",
    };
    SharedPreferences sharedPreferences;

    public PointFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        tblPoint = view.findViewById(R.id.tblPoint);
        if (sharedPreferences != null) {
            String msv = sharedPreferences.getString("MSV",null);
            if (msv != null) {
                ShowPoint showPoint = new ShowPoint();
                showPoint.execute(msv);
            }
        }
        return view;
    }

    static class TablePoint extends TableMainLayout {
        public static ArrayList<CalendarSubject> list = new ArrayList<>();
        public TablePoint(Context context, String[] headers) {
            super(context, headers);
        }

        @Override
        public List<SampleObject> sampleObjects() {
            System.out.println("LIST_POINT "+list.size());
            List<SampleObject> sampleObjects = new ArrayList<>();
            for(CalendarSubject calendarSubject : this.list ){
                String cc=null,gk=null,ck=null,tb=null,chu=null;
                for (Point point : calendarSubject.getDsDiem()) {
                    cc = point.getDiemCC();
                    gk = point.getDiemGK();
                    ck = point.getDiemCK();
                    tb = point.getDiemTB();
                    chu = point.getDiemChu();
                    SampleObject sampleObject = new SampleObject(
                            calendarSubject.getMonhoc(),
                            "\n"+cc + "\n",
                            gk,
                            ck,
                            tb,
                            chu,
                            null,
                            null
                    );
                    sampleObjects.add(sampleObject);
                }
            }
            return sampleObjects;
        }
    }

    class ShowPoint extends PointTask {

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
            TablePoint.list.clear();
            for (CalendarSubject calendarSubject : calendarSubjects) {
                TablePoint.list.add(calendarSubject);
            }
            TablePoint tablePoint = new TablePoint(getContext(),headers);
            tblPoint.addView(tablePoint);
            progressDialog.dismiss();

        }


    }
}