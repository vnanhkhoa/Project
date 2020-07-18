package com.sict.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.drink.adapter.AdapterDoUong;
import com.sict.drink.model.DoUong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoUongActivity extends AppCompatActivity {
    DatabaseReference myRef;
    ImageView anh;
    TextView txtTen,txtGiatb,txtDiaChi,txtHmoCua;
    RecyclerView listMenu;
    ArrayList<DoUong> dsDoUongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_uong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myRef = FirebaseDatabase.getInstance().getReference();
        addControls();
    }

    private void addControls() {
        anh = findViewById(R.id.anh);
        txtTen = findViewById(R.id.txtten);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGiatb = findViewById(R.id.txtGiatb);
        txtHmoCua = findViewById(R.id.txtHmoCua);
        dsDoUongs = new ArrayList<>();
        listMenu = findViewById(R.id.listMenu);
        listMenu.setLayoutManager(new LinearLayoutManager(DoUongActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        String id = (intent.hasExtra("ID")) ? intent.getStringExtra("ID") : "1" ;
        dsDoUongs.clear();
        myRef.child("quan").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtGiatb.setText(snapshot.child("giatb").getValue().toString());
                txtDiaChi.setText(snapshot.child("diachi").getValue().toString());
                txtTen.setText(snapshot.child("ten").getValue().toString());
                txtHmoCua.setText(snapshot.child("giomocua").getValue().toString());
                Picasso.get().load(snapshot.child("anh").getValue().toString()).into(anh);
                setTitle(txtTen.getText());
                for (DataSnapshot dataSnapshot : snapshot.child("douong").getChildren()) {
                    DoUong doUong = new DoUong();
                    doUong.setAnh(dataSnapshot.child("anh").getValue().toString());
                    doUong.setGia(dataSnapshot.child("gia").getValue().toString());
                    doUong.setTen(dataSnapshot.child("ten").getValue().toString());
                    dsDoUongs.add(doUong);
                }
                AdapterDoUong adapterDoUong = new AdapterDoUong(DoUongActivity.this,dsDoUongs);
                listMenu.setAdapter(adapterDoUong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                MainActivity.gioHangs.clear();
                startActivity(logout);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }
}