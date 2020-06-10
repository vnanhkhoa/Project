package com.example.khachsan;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khachsan.model.Phong;
import com.example.khachsan.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtUser,txtPass;
    TextView txtInfor;
    Button btnLogin;

    ListView lvPhong;
    ArrayList<Phong> dsPhong;
    ArrayAdapter<Phong> phongArrayAdapter;
    UrlService urlService = new UrlService();
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyLogin();
            }
        });
    }

    private void xulyLogin() {
        String user = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        UserTask userTask = new UserTask();
        userTask.execute(user,pass);
    }

    private void addControls() {
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tc");
        tab1.setIndicator("Home");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tt");
        tab2.setIndicator("Information");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        txtUser = findViewById(R.id.txtuser);
        txtPass = findViewById(R.id.txtpass);
        txtInfor = findViewById(R.id.txtInfor);
        btnLogin = findViewById(R.id.btnLogin);

        lvPhong = findViewById(R.id.lvPhong);
        dsPhong = new ArrayList<>();
        phongArrayAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsPhong
        );
        lvPhong.setAdapter(phongArrayAdapter);
        PhongTask phongTask = new PhongTask();
        phongTask.execute();

    }

    class PhongTask extends AsyncTask<Void,Void,ArrayList<Phong>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            phongArrayAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<Phong> phongs) {
            super.onPostExecute(phongs);
            phongArrayAdapter.clear();
            phongArrayAdapter.addAll(phongs);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<Phong> doInBackground(Void... voids) {
            ArrayList<Phong> phongs = new ArrayList<>();
            try {
                URL url=new URL(urlService.URL+"Show/room");
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
                br.close();
                is.close();
                isr.close();
                JSONArray jsonArray= new JSONArray(json);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject item=jsonArray.getJSONObject(i);
                    Phong tiGia=new Phong();
                    if(item.has("idp"))
                        tiGia.setMaPhong(item.getString("idp"));
                    if(item.has("tenphong")) {
                        tiGia.setTenPhong(item.getString("tenphong"));
                    }
                    if(item.has("iddm")) {
                        tiGia.setIddm(item.getString("iddm"));
                    }
                    phongs.add(tiGia);
                }
                connection.disconnect();
                Log.d("JSON_DONGA",json);
            } catch (Exception ex)
            {
                Log.e("LOI",ex.toString());
            }
            return phongs;
        }
    }
    class UserTask extends AsyncTask<String,Void,User>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtInfor.setText("");
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            txtInfor.setText("");
            if (user.getError()==null)
            {
                txtInfor.setText(user.toString());
            }
            else {
                Toast.makeText(MainActivity.this,user.toString(),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected User doInBackground(String... strings) {
            User user = new User();
            try {
                URL url=new URL(urlService.URL+"Login");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(strings[0],"UTF-8")+
//                        "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(strings[1],"UTF-8");
                Uri.Builder builders = new Uri.Builder()
                        .appendQueryParameter("username", strings[0])
                        .appendQueryParameter("password", strings[1]);
                String query = builders.build().getEncodedQuery();
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();

                int response_code = connection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK)
                {
                    InputStreamReader isr=new InputStreamReader(connection.getInputStream(),"UTF-8");
                    BufferedReader br=new BufferedReader(isr);
                    String line=br.readLine();
                    StringBuilder builder=new StringBuilder();
                    while (line!=null)
                    {
                        builder.append(line);
                        line=br.readLine();
                    }
                    String json=builder.toString();
                    br.close();
                    isr.close();
                    JSONObject jsonObject = new JSONObject(json);
//                User user = new User();
                    if(jsonObject.has("id"))
                        user.setId(jsonObject.getString("id"));
                    if(jsonObject.has("fullname")) {
                        user.setFullName(jsonObject.getString("fullname"));
                    }
                    if(jsonObject.has("taikhoan")) {
                        user.setUserName(jsonObject.getString("taikhoan"));
                    }
                    if(jsonObject.has("matkhau")) {
                        user.setPassWord(jsonObject.getString("matkhau"));
                    }
                    if(jsonObject.has("error")) {
                        user.setError(jsonObject.getString("error"));
                    }
                    Log.d("JSON_Login",json);
                    connection.disconnect();
                    return user;
                }
            }
            catch (Exception ex){
                Log.e("Loi",ex.toString());
            }
            return null;
        }
    }
}
