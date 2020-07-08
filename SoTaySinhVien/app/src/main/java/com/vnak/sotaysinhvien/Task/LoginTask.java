package com.vnak.sotaysinhvien.Task;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.vnak.sotaysinhvien.Server;
import com.vnak.sotaysinhvien.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<String,Void, User> {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    @Override
    protected User doInBackground(String... strings) {
        try {
            URL url=new URL(new Server().URLSERVER+"api/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            Uri.Builder builders = new Uri.Builder()
                    .appendQueryParameter("email", strings[0])
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
                JSONObject jsonObject = new JSONObject(json);

//                if(jsonObject.has("error")) {
//                user.setError(jsonObject.getString("error"));
//                }
//                if(jsonObject.has("id")) {
//                    user.setId(jsonObject.getString("id"));
//                }
//                if(jsonObject.has("fullname")) {
//                    user.setFullName(jsonObject.getString("fullname"));
//                }
//                if(jsonObject.has("username")) {
//                    user.setUserName(jsonObject.getString("username"));
//                }
//                if(jsonObject.has("password")) {
//                    user.setPassWord(jsonObject.getString("password"));
//                }
                Log.d("JSON_Login",json);
                br.close();
                isr.close();
                connection.disconnect();
//                return user;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
