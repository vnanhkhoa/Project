package com.vnakhoa.luxury;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vnakhoa.luxury.adapter.ImgAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcView;
    ArrayList<Integer> arrImg;

    EditText txtTra,txtDat;
    ImageButton btnDat,btnTra;
    Button btnKT;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("dd/MM/YYYY");


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addControls() {
        rcView = findViewById(R.id.rcView);
        arrImg = new ArrayList<>();
        arrImg.add(R.drawable.banner1);
        arrImg.add(R.drawable.banner2);
        arrImg.add(R.drawable.banner3);

        ImgAdapter imgAdapter = new ImgAdapter(arrImg,MainActivity.this);
        rcView.setAdapter(imgAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        rcView.setLayoutManager(linearLayoutManager);
        txtDat = findViewById(R.id.txtDat);
        txtTra = findViewById(R.id.txtTra);
        btnDat = findViewById(R.id.btnDat);
        btnTra = findViewById(R.id.btnTra);
        btnKT = findViewById(R.id.btnKiemTra);


    }

    private void addEvent() {
        btnDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyDate(txtDat);
            }
        });
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyDate(txtTra);
            }
        });
        btnKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PhongActivity.class);
                intent.putExtra("ngay_dat",txtDat.getText().toString());
                intent.putExtra("ngay_tra",txtTra.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void xulyDate(final TextView textView) {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                textView.setText(spf.format(calendar.getTime()));
                Log.e("DATE",calendar.getTime().toString());
            }
        };
        DatePickerDialog date = new DatePickerDialog(
                MainActivity.this,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        date.show();
    }
}
