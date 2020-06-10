package com.vnakhoa.floatingmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.vnakhoa.floatingmenu.adapter.SinhVienAdapter;
import com.vnakhoa.floatingmenu.model.SinhVien;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcSinhVien;
    ArrayList<SinhVien> listSinhVien;
    SinhVienAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        rcSinhVien = findViewById(R.id.rcSinhVien);
        listSinhVien = new ArrayList<>();
        listSinhVien.add(new SinhVien(1,"Ronaldo","JUV",R.drawable.ronadol));
        listSinhVien.add(new SinhVien(1,"Messi","BAC",R.drawable.messi));
        listSinhVien.add(new SinhVien(1,"Pogba","MU",R.drawable.pogba));
        listSinhVien.add(new SinhVien(1,"Bruno","MU",R.drawable.bruno));
        listSinhVien.add(new SinhVien(1,"Bale","RMD",R.drawable.bale));

        adapter = new SinhVienAdapter(MainActivity.this,listSinhVien);
        rcSinhVien.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        RecyclerView.LayoutManager layoutManager = manager;
        rcSinhVien.setLayoutManager(layoutManager);

    }
}
