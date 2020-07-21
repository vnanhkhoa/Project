package com.sict.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.drink.adapter.AdapterQuan;
import com.sict.drink.model.GioHang;
import com.sict.drink.model.Quan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private RecyclerView listQuan;
    private ArrayList<Quan> dsQuan;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    ArrayList<String> mangqc;
    public static ArrayList<GioHang> gioHangs;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        gioHangs = new ArrayList<>();
        Intent intent = getIntent();
        if (intent.hasExtra("GIOHANG")) {
            ArrayList<GioHang> a = (ArrayList<GioHang>) intent.getSerializableExtra("GIOHANG");
            gioHangs.addAll(a);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        };
        addControls();
        Actionview();
    }
    private void Actionview() {
        mangqc.add("https://www.highlandscoffee.com.vn/vnt_upload/weblink/HL20_2000x639_1.png");
        mangqc.add("https://www.highlandscoffee.com.vn/vnt_upload/weblink/HCO-7605-FESTIVE-2020-WEB-FB-2000X639_1.png");
        mangqc.add("https://gongcha.com.vn/wp-content/uploads/2020/02/Cover-Web-giá-gốc-TQA-đường-nâu.jpg");

        for (int i=0 ; i < mangqc.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get()
                    .load(mangqc.get(i))
                    .error(R.drawable.error)
                    .fit()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void addControls() {
        listQuan = findViewById(R.id.list_quan);
        listQuan.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        dsQuan = new ArrayList<>();
        viewFlipper = findViewById(R.id.viewflipper);
        mangqc = new ArrayList<>();
        readData();
    }

    public void readData() {
        dsQuan.clear();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        myRef.child("quan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Quan quan = new Quan();
                    quan.setAnh(dataSnapshot.child("anh").getValue().toString());
                    quan.setDiaChi(dataSnapshot.child("diachi").getValue().toString());
                    quan.setGiatb(dataSnapshot.child("giatb").getValue().toString());
                    quan.setGiomocua(dataSnapshot.child("giomocua").getValue().toString());
                    quan.setTen(dataSnapshot.child("ten").getValue().toString());
                    quan.setId(dataSnapshot.getKey());
                    dsQuan.add(quan);
                }
                AdapterQuan adapterQuan = new AdapterQuan(MainActivity.this,dsQuan);
                listQuan.setAdapter(adapterQuan);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LOI", error.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
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
                mAuth.signOut();
                gioHangs.clear();
                startActivity(logout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}