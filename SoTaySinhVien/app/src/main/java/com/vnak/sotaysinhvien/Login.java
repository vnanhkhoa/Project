package com.vnak.sotaysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnak.sotaysinhvien.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login extends AppCompatActivity {
    TextInputLayout txtPassword;
    TextInputLayout txtEmail;
    Button btnLogin;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validete(txtEmail) | !validete(txtPassword)){
                    return;
                }
                String email = txtEmail.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();

                LoginTask loginTask = new LoginTask();
                loginTask.execute(email,password);
                
            }
        });
    }

    private void addControls() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private boolean validete(TextInputLayout textInputLayout) {
        boolean check = true ;
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không thể để trống");
            return false;
        }
        textInputLayout.setError("");
        return check;
    }

    class LoginTask extends AsyncTask<String,Void, User>
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if (user.getError() == null)
            {
                Intent intent = new Intent(Login.this,MainActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
            else {
                Toast.makeText(Login.this,user.getError(),Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected User doInBackground(String... strings) {
            User user = new User();
            try {
                URL url=new URL(new Service().URLSERVER+"api/login");
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

                    if(jsonObject.has("error")) {
                        user.setError(jsonObject.getString("error"));
                        return user;
                    }
                    if(jsonObject.has("id")) {
                        user.setId(jsonObject.getString("id"));
                    }
                    if(jsonObject.has("ten")) {
                        user.setName(jsonObject.getString("ten"));
                    }
                    if(jsonObject.has("msv")) {
                        user.setMsv(jsonObject.getString("msv"));
                    }
                    if(jsonObject.has("email")) {
                        user.setEmail(jsonObject.getString("email"));
                    }
                    if(jsonObject.has("ngay_sinh")) {
                        user.setBirth(jsonObject.getString("ngay_sinh"));
                    }
                    if(jsonObject.has("gioi_tinh")) {
                        user.setGender(jsonObject.getString("gioi_tinh"));
                    }
                    if(jsonObject.has("dia_chi")) {
                        user.setAddress(jsonObject.getString("dia_chi"));
                    }
                    if(jsonObject.has("ten_lop")) {
                        user.setLop(jsonObject.getString("ten_lop"));
                    }
                    if(jsonObject.has("sdt")) {
                        user.setSdt(jsonObject.getString("sdt"));
                    }
                    Log.d("JSON_Login",json);
                    br.close();
                    isr.close();
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