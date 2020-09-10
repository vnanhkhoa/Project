package com.vnak.sotaysinhvien.Task;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.vnak.sotaysinhvien.Service;
import com.vnak.sotaysinhvien.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.vnak.sotaysinhvien.Login.CONNECTION_TIMEOUT;
import static com.vnak.sotaysinhvien.Login.READ_TIMEOUT;

public class UpdateUser extends AsyncTask<User,Void,Boolean> {

    @Override
    protected Boolean doInBackground(User... users) {
        User user = users[0];
        try {
            URL url=new URL(new Service().URLSERVER+"api/updateSV");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            Uri.Builder builders = new Uri.Builder()
                    .appendQueryParameter("name", user.getName())
                    .appendQueryParameter("msv", user.getMsv())
                    .appendQueryParameter("gender", user.getGender())
                    .appendQueryParameter("birth", user.getBirth())
                    .appendQueryParameter("sdt", user.getSdt())
                    .appendQueryParameter("address",user.getAddress());
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
                Boolean json= Boolean.valueOf(builder.toString());
                br.close();
                isr.close();
                connection.disconnect();
                return json;

            }
        }
        catch (Exception ex){
            Log.e("Loi",ex.toString());
        }
        return false;
    }
}
