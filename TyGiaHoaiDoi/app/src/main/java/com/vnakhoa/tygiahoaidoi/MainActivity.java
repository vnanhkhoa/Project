package com.vnakhoa.tygiahoaidoi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.vnakhoa.tygiahoaidoi.adapter.AdapterTyGia;
import com.vnakhoa.tygiahoaidoi.model.TyGia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<TyGia> dsTyGia;
    AdapterTyGia adapterTyGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        listView = findViewById(R.id.lvDs);
        dsTyGia = new ArrayList<>();
        adapterTyGia = new AdapterTyGia(
                MainActivity.this,
                R.layout.item,
                dsTyGia
        );
        listView.setAdapter(adapterTyGia);

        TyGiaTask tyGiaTask = new TyGiaTask();
        tyGiaTask.execute();
    }

    class TyGiaTask extends AsyncTask<Void,Void,ArrayList<TyGia>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapterTyGia.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<TyGia> tyGias) {
            super.onPostExecute(tyGias);
            adapterTyGia.clear();
            adapterTyGia.addAll(tyGias);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<TyGia> doInBackground(Void... voids) {
            ArrayList<TyGia> ds = new ArrayList<>();
            try {
                URL url=new URL("https://www.dongabank.com.vn/exchange/export");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");

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
                json=json.replace("(", "");
                json=json.replace(")","");

                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray= jsonObject.getJSONArray("items");
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject item=jsonArray.getJSONObject(i);
                    TyGia tiGia=new TyGia();
                    if(item.has("type"))
                        tiGia.setType(item.getString("type"));
                    if(item.has("imageurl")) {
                        tiGia.setImageurl(item.getString("imageurl"));
                        url=new URL(tiGia.getImageurl());
                        connection= (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                        connection.setRequestProperty("Accept", "*/*");
                        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                        tiGia.setBitmap(bitmap);
                    }
                    if(item.has("muatienmat")) {
                        tiGia.setMuatienamt(item.getString("muatienmat"));
                    }
                    if(item.has("muack")) {
                        tiGia.setMuack(item.getString("muack"));
                    }
                    if(item.has("bantienmat")) {
                        tiGia.setBantienmat(item.getString("bantienmat"));
                    }
                    if(item.has("banck")) {
                        tiGia.setBanck(item.getString("banck"));
                    }
                    ds.add(tiGia);
                }
                Log.d("JSON_DONGA",json);

            }catch (Exception e){
                Log.e("Loi",e.toString());
            }
            return ds;
        }



    }

}
