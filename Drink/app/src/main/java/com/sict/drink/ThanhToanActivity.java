package com.sict.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sict.drink.adapter.AdapterGioHang;
import com.sict.drink.model.GioHang;
import com.sict.drink.model.HoaDon;

import java.text.BreakIterator;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {
    private static int tong;
    TextInputLayout txtName,txtSDT,txtDiaChi;
    RecyclerView listHoaDon;
    static TextView txtSl,txttongtien;
    Button btnXacNhan;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        setTitle("Thanh Toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ThanhToanActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                hoaDon();
            }
        });
    }

    private void hoaDon () {
        String date = dateFormat.format(calendar.getTime());
        String name = txtName.getEditText().getText().toString().trim();
        String sdt = txtSDT.getEditText().getText().toString().trim();
        String diachi = txtDiaChi.getEditText().getText().toString().trim();
        HoaDon hoaDon = new HoaDon(name,sdt,diachi,MainActivity.gioHangs,tong);
        Map<String,Object> hObjectMap = hoaDon.toMap();
        System.out.println("ID_USER: "+calendar.getTimeInMillis());
        String id = mAuth.getCurrentUser().getUid();
        System.out.println("ID_USER: "+hObjectMap.toString());
        System.out.println("ID_USER: "+id+" DATE: "+date);
        myRef.child("hoadon").child(id).child(date).child(String.valueOf(calendar.getTimeInMillis())).setValue(hObjectMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThanhToanActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("LOI "+e);
                progressDialog.dismiss();
            }
        });

    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtSDT = findViewById(R.id.txtSDT);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSl = findViewById(R.id.txtSl);
        txttongtien = findViewById(R.id.txttongtien);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        listHoaDon = findViewById(R.id.listHoaDon);
        AdapterGioHang adapterGioHang = new AdapterGioHang(ThanhToanActivity.this,MainActivity.gioHangs);
        listHoaDon.setAdapter(adapterGioHang);
        listHoaDon.setLayoutManager(new LinearLayoutManager(this));
        sumTT();
    }
    public static void sumTT() {
        int sl = 0;
        tong = 0;
        for (GioHang gioHang : MainActivity.gioHangs) {
            tong = tong + Integer.parseInt(gioHang.getDoUong().getGia())*gioHang.getSoLuong();
            sl += gioHang.getSoLuong();
        }
        txttongtien.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(tong) +".000đ");
        txtSl.setText(sl+"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tt,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("GIOHANG",MainActivity.gioHangs);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}