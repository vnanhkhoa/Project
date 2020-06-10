package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        txtSms = findViewById(R.id.txtSms);
        showSms();
    }

    private void showSms() {
        StringBuilder stringBuilder = new StringBuilder();
        Uri uri = Uri.parse("content://sms/");
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        startManagingCursor(c);

        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                stringBuilder.append((i+1)+".  "+c.getString(c.getColumnIndexOrThrow("address")).toString()).append("\n").append("      "+c.getString(c.getColumnIndexOrThrow("body")).toString()).append("\n");
                c.moveToNext();
            }
        }
        c.close();
        txtSms.setText(stringBuilder.toString());

    }
}
