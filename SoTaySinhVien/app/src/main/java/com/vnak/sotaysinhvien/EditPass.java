package com.vnak.sotaysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnak.sotaysinhvien.fragment.HomeFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.vnak.sotaysinhvien.Login.CONNECTION_TIMEOUT;
import static com.vnak.sotaysinhvien.Login.READ_TIMEOUT;

public class EditPass extends AppCompatActivity {

    TextInputLayout txtPassOld,txtPassNew;
    Button btnSave,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        addControlls();
        addEvents();

    }

    private void addEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validete(txtPassNew) | !validete(txtPassOld)) return;
                SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                if (preferences != null) {
                    String email = preferences.getString("Email",null);
                    if (email != null) {
                        String pass_old = txtPassOld.getEditText().getText().toString().trim();
                        String pass_new = txtPassNew.getEditText().getText().toString().trim();
                        EditPassTask editPassTask = new EditPassTask();
                        editPassTask.execute(email,pass_old,pass_new);
                    }
                }
            }
        });

    }

    private boolean validete(TextInputLayout textInputLayout) {
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không để trống");
            return false;
        }
        textInputLayout.setError("");
        return true;
    }

    private void addControlls() {
        txtPassOld = findViewById(R.id.txtPassOld);
        txtPassNew = findViewById(R.id.txtPassNew);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    class EditPassTask extends AsyncTask <String,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.equals(true)) {
                Toast.makeText(EditPass.this,"Đổi Mật Khẩu Thành Công",Toast.LENGTH_SHORT).show();
                finish();
            } else if (aBoolean.equals(false)) {
                Toast.makeText(EditPass.this,"Mật Khẩu Cũ Không Đúng",Toast.LENGTH_SHORT).show();
            } else if (aBoolean.equals(null)) {
                Toast.makeText(EditPass.this,"Đổi Mật Khẩu Thất Bại",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String email = strings[0];
            String passOld = strings[1];
            String passNew = strings[2];
            try {
                URL url=new URL(new Service().URLSERVER+"api/editpass");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setDoOutput(true);
                connection.setDoInput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                Uri.Builder builders = new Uri.Builder()
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("pass_old", passOld)
                        .appendQueryParameter("pass_new", passNew);
                String query = builders.build().getEncodedQuery();
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();

                int response_code = connection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line = br.readLine();
                    StringBuilder builder = new StringBuilder();
                    while (line != null) {
                        builder.append(line);
                        line = br.readLine();
                    }
                    Boolean json= Boolean.valueOf(builder.toString());
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}