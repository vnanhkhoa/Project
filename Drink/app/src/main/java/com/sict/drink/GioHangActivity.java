package com.sict.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sict.drink.adapter.AdapterGioHang;
import com.sict.drink.model.GioHang;

import java.text.NumberFormat;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity {
    public RecyclerView list_gio_hang;
    public static TextView txtTongTien;
    Button btnThanhToan,btnTT;
    AdapterGioHang adapterGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Giỏ Hàng");
        addControls();
        addEvents();
    }

    private void addControls() {
        list_gio_hang = findViewById(R.id.list_gio_hang);
        txtTongTien = findViewById(R.id.txttongtien);
        btnThanhToan = findViewById(R.id.btnthanhtoan);
        btnTT = findViewById(R.id.btnttmuahang);
        adapterGioHang = new AdapterGioHang(GioHangActivity.this,MainActivity.gioHangs);
        list_gio_hang.setAdapter(adapterGioHang);
        list_gio_hang.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if (MainActivity.gioHangs.size() == 0) {
            btnThanhToan.setVisibility(View.INVISIBLE);
        }
        sum();
    }

    private void addEvents() {
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this,ThanhToanActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void sum() {
        int sum = 0;
        for (GioHang gioHang : MainActivity.gioHangs) {
            sum = sum + Integer.parseInt(gioHang.getDoUong().getGia())*gioHang.getSoLuong();
        }
        txtTongTien.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(sum) +".000đ");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}