package com.vnak.sotaysinhvien.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.vnak.sotaysinhvien.Server;
import com.vnak.sotaysinhvien.model.CalendarST;
import com.vnak.sotaysinhvien.model.CalendarSubject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class LichHocTask extends AsyncTask<String,Void, ArrayList<CalendarSubject>> {
    @Override
    protected ArrayList<CalendarSubject> doInBackground(String... strings) {
        ArrayList<CalendarSubject> list = new ArrayList<>();
        String urlservice = new Server().URLSERVER+"api/calendar/"+strings[0];
        try {
            URL url=new URL(urlservice);
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
            System.out.println("JSON "+ list.get(1).getList_lich_hoc().get(1).toString());
        }
        catch (Exception e)
        {
            Log.e("Loi",e.toString());
        }
        return list;
    }
}
