package com.vnak.sotaysinhvien.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.vnak.sotaysinhvien.Service;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.Point;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PointTask extends AsyncTask<String,Void, ArrayList<CalendarSubject>> {
    @Override
    protected ArrayList<CalendarSubject> doInBackground(String... strings) {
        ArrayList<CalendarSubject> list = new ArrayList<>();
        try {
            URL url=new URL(new Service().URLSERVER+"api/point/"+strings[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json; charset=utf-8");

            InputStream is= connection.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"UTF-8");
            BufferedReader br=new BufferedReader(isr);
            String line=br.readLine();
            StringBuilder builder=new StringBuilder();
            while (line!=null)
            {
                builder.append(line);
                line=br.readLine();
            }
            String json=builder.toString();
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject item=jsonArray.getJSONObject(i);
                CalendarSubject calendarSubject = new CalendarSubject();
                ArrayList<Point> points = new ArrayList<>();
                Point point = new Point();
                if (item.has("ten_mon_hoc"))
                {
                    calendarSubject.setMonhoc(item.getString("ten_mon_hoc"));
                }

                if (item.has("diem_cc"))
                {
                    point.setDiemCC(item.getString("diem_cc"));
                }
                if (item.has("diem_gk"))
                {
                    point.setDiemGK(item.getString("diem_gk"));
                }
                if (item.has("diem_ck"))
                {
                    point.setDiemCK(item.getString("diem_ck"));
                }
                if (item.has("diem_tb"))
                {
                    point.setDiemTB(item.getString("diem_tb"));
                }
                if (item.has("diem_chu"))
                {
                    point.setDiemChu(item.getString("diem_chu"));
                }
                points.add(point);
                calendarSubject.setDsDiem(points);
                list.add(calendarSubject);
            }
            Log.d("JSON_Phong",json);
        }
        catch (Exception e)
        {
            Log.e("Loi",e.toString());
        }
        return list;
    }
}
