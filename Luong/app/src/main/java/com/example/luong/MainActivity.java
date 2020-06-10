package com.example.luong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luong.model.DanhBa;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhBa;
    ArrayList<DanhBa> danhBa;
    ArrayAdapter<DanhBa> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        lvDanhBa = findViewById(R.id.lvDanhBa);
        danhBa = new ArrayList<>();
        adapter = new ArrayAdapter<>(MainActivity.this
                ,android.R.layout.simple_list_item_1,
                danhBa);
        lvDanhBa.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
           if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS)){

           }
           else {
               ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},121);
           }
        }else {
            contactDB();
        }

    }

    private void contactDB() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        danhBa.clear();

        while (cursor.moveToNext())
        {
            String id = ContactsContract.Contacts.DISPLAY_NAME;
            int clname = cursor.getColumnIndex(id);
            String name = cursor.getString(clname);

            String idphone = ContactsContract.CommonDataKinds.Phone.NUMBER;
            int colPhone = cursor.getColumnIndex(idphone);
            String phone = cursor.getString(colPhone);
            DanhBa db = new DanhBa(name,phone);
            danhBa.add(db);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 121){
            if (permissions.length>0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    contactDB();
                }
            }
        }
    }
}


