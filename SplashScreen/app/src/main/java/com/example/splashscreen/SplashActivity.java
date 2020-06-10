package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView txtPhamTram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        addControls();

    }

    private void addControls() {
        progressBar = findViewById(R.id.progressBar);
        txtPhamTram = findViewById(R.id.txtPhamTram);
        PhanTramTask phanTramTask = new PhanTramTask();
        phanTramTask.execute();
    }
    class PhanTramTask extends AsyncTask<Void,Integer,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int i = values[0];
            progressBar.setProgress(i);
            txtPhamTram.setText(String.valueOf(i).toString()+"%");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (int i =0;i<=100;i++){
                try {
                    publishProgress(i);
                    Thread.sleep(500);
                }
                catch (Exception ex)
                {
                    Log.e("Loi",ex.toString());
                }
            }
            return null;
        }
    }
}
