package com.vnak.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcyView;
    ArrayList<Song> dsSong;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Nguyễn Đình Đức");
        rcyView = findViewById(R.id.rcyView);
        dsSong = new ArrayList<>();
        dsSong.add(new Song(R.drawable.a,"Bang Bang Bang","Yooh A"));
        dsSong.add(new Song(R.drawable.a,"La La La","Mr Tì"));
        dsSong.add(new Song(R.drawable.a,"No Body","Goa Ka"));
        dsSong.add(new Song(R.drawable.a,"Bang Bang Bang","Yooh A"));
        dsSong.add(new Song(R.drawable.a,"La La La","Mr Tì"));
        dsSong.add(new Song(R.drawable.a,"No Body","Goa Ka"));
        dsSong.add(new Song(R.drawable.a,"Bang Bang Bang","Yooh A"));
        dsSong.add(new Song(R.drawable.a,"La La La","Mr Tì"));
        dsSong.add(new Song(R.drawable.a,"No Body","Goa Ka"));
        dsSong.add(new Song(R.drawable.a,"Bang Bang Bang","Yooh A"));
        dsSong.add(new Song(R.drawable.a,"La La La","Mr Tì"));
        dsSong.add(new Song(R.drawable.a,"No Body","Goa Ka"));
        adapter = new Adapter(MainActivity.this,dsSong);
        rcyView.setAdapter(adapter);
        rcyView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}