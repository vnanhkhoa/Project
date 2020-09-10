package com.vnak.sotaysinhvien.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.vnak.sotaysinhvien.Service;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LichHocTask extends AsyncTask<String,Void, ArrayList<CalendarSubject>> {
    @Override
    protected ArrayList<CalendarSubject> doInBackground(String... strings) {
        ArrayList<CalendarSubject> list = new ArrayList<>();
        try {
            URL url=new URL(strings[0]);
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
                if (item.has("monhoc"))
                {
                    calendarSubject.setMonhoc(item.getString("monhoc"));
                }
                if (item.has("lich_hoc"))
                {
                    ArrayList<CalendarST> calendarSTS = new ArrayList<>();

                    JSONArray array = item.getJSONArray("lich_hoc");
                    for (int j = 0;j<array.length();j++) {
                        JSONObject jsonObject = array.getJSONObject(j);
                        CalendarST calendarST = new CalendarST();
                        if (jsonObject.has("tiet_hoc")) {
                            calendarST.setTiet(jsonObject.getString("tiet_hoc"));
                        }
                        if (jsonObject.has("ten_phong")) {
                            calendarST.setPhong(jsonObject.getString("ten_phong"));
                        }
                        if (jsonObject.has("thoi_gian")) {
                            calendarST.setThoigian(jsonObject.getString("thoi_gian"));
                        }
                        calendarSTS.add(calendarST);
                    }
                    calendarSubject.setList_lich_hoc(calendarSTS);
                }

                list.add(calendarSubject);
            }
            Log.d("JSON_Phong",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("Loi",e.toString());
        }
        return list;
    }
}
