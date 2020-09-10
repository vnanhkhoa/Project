package com.vnak.sotaysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnak.sotaysinhvien.adapter.AdapterWork;
import com.vnak.sotaysinhvien.fragment.HomeFragment;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThemLichCNActivity extends AppCompatActivity {
    Calendar timer = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
    TextInputLayout txtChuDe,txtDiaChi;
    TextView txtGioBD,txtGioKT;
    Button btnThemLich;
    Spinner thu;
    String[] dsThu;
    ArrayAdapter<String> adapterThu;
    RecyclerView listWord;
    ArrayList<CalendarSubject> dsCV;
    AdapterWork adapterWork;
    AutoCompleteTextView chude;
    ArrayList<String> works;
    public static ArrayAdapter<String> listWork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lich_c_n);
        addControls();
        setTitle("Thêm Lịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        addEvents();
    }

    private void addEvents() {
        txtGioKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThoiGian(txtGioKT);
            }
        });
        txtGioBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThoiGian(txtGioBD);
            }
        });

        btnThemLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validete(txtDiaChi) | !validete(txtChuDe)){
                    return;
                }
                xuLyThemLich();
            }
        });
    }

    private boolean validete(TextInputLayout textInputLayout) {
        boolean check = true ;
        String text = textInputLayout.getEditText().getText().toString();
        if (text.isEmpty()) {
            textInputLayout.setError(textInputLayout.getHint()+" không thể để trống");
            return false;
        }
        textInputLayout.setError("");
        return check;
    }


    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences("Work", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(dsCV);
        editor.putString("work",json);
        editor.commit();
    }

    public boolean checkCalendarLike(String date,String chuDe,String diaChi,String time) {
        for (CalendarSubject cv : dsCV) {
            for (Work work : cv.getDsWork()) {
                if (work.getDiaDiem().equals(diaChi)
                        && work.getGio().equals(time) && work.getThoiGian().equals(date)
                        || work.getGio().equals(time) && work.getThoiGian().equals(date)) {
                    Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }

    private void xuLyThemLich() {
        String date = (!thu.getSelectedItem().equals("Chủ Nhật")) ? "T"+((String) thu.getSelectedItem()).substring(4) : "CN";
        String chuDe = txtChuDe.getEditText().getText().toString().trim();
        String diaChi = txtDiaChi.getEditText().getText().toString().trim();
        String thoiGian = txtGioBD.getText().toString()+"->"+txtGioKT.getText().toString();
        ArrayList<Work> list;
        Boolean check = true;

        if (dsCV.size() > 0) {
            if (checkCalendarLike(date,chuDe,diaChi,thoiGian)) return;
            for (CalendarSubject cv : dsCV) {
                if (cv.getMonhoc().equals(chuDe)) {
                    list = cv.getDsWork();
                    Work w = new Work();
                    w.setDiaDiem(diaChi);
                    w.setGio(thoiGian);
                    w.setThoiGian(date);
                    list.add(w);
                    cv.setDsWork(list);
                    adapterWork.notifyDataSetChanged();
                    txtDiaChi.getEditText().setText("");
                    txtChuDe.getEditText().setText("");
                    Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
                    check = false;
                    break;
                }
            }
            if (check == true) {
                CalendarSubject cv = new CalendarSubject();
                cv.setMonhoc(chuDe);
                list = new ArrayList<>();
                Work work = new Work();
                work.setDiaDiem(diaChi);
                work.setGio(thoiGian);
                work.setThoiGian(date);
                list.add(work);
                cv.setDsWork(list);
                dsCV.add(cv);
                listWork.add(chuDe);
                adapterWork.notifyDataSetChanged();
                txtChuDe.getEditText().setText("");
                txtDiaChi.getEditText().setText("");
                Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
            }
        } else {
            CalendarSubject cv = new CalendarSubject();
            cv.setMonhoc(chuDe);
            list = new ArrayList<>();
            Work work = new Work();
            work.setDiaDiem(diaChi);
            work.setGio(thoiGian);
            work.setThoiGian(date);
            list.add(work);
            cv.setDsWork(list);
            dsCV.add(cv);
            listWork.add(chuDe);
            adapterWork.notifyDataSetChanged();
            txtChuDe.getEditText().setText("");
            txtDiaChi.getEditText().setText("");
            Toast.makeText(getApplicationContext(),"Thêm Lịch Thành Công",Toast.LENGTH_SHORT).show();
        }
    }

    private void xuLyThoiGian(final TextView textView) {
        try {
            if (textView.getText().toString().length() > 0) {
                timer.setTime(spf.parse(textView.getText().toString()));
            }
            TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timer.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    timer.set(Calendar.MINUTE,minute);
                    timer.set(Calendar.SECOND,0);
                    textView.setText(spf.format(timer.getTime()));
                }
            };
            TimePickerDialog time = new TimePickerDialog(
                    ThemLichCNActivity.this,
                    callBack,
                    timer.get(Calendar.HOUR_OF_DAY),
                    timer.get(Calendar.MINUTE),
                    true
            );
            time.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addControls() {
        txtChuDe = findViewById(R.id.txtChuDe);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGioBD = findViewById(R.id.txtGioBD);
        txtGioKT = findViewById(R.id.txtGioKT);
        btnThemLich = findViewById(R.id.btnThemLich);
        chude = findViewById(R.id.chude);
        thu = findViewById(R.id.spnThu);
        listWord = findViewById(R.id.listWord);
        dsThu = getResources().getStringArray(R.array.Date);
        adapterThu = new ArrayAdapter<>(ThemLichCNActivity.this,android.R.layout.simple_list_item_1,dsThu);
        thu.setAdapter(adapterThu);
        txtGioBD.setText(spf.format(timer.getTime()));
        txtGioKT.setText(spf.format(timer.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dsCV = new ArrayList<>();
        works = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("Work", Context.MODE_PRIVATE);
        if (preferences != null) {
            Gson gson = new Gson();
            String json = preferences.getString("work",null);
            if (json != null) {
                ArrayList<CalendarSubject> list = gson.fromJson(json, new TypeToken<ArrayList<CalendarSubject>>(){}.getType());
                for (CalendarSubject subject : list) {
                    dsCV.add(subject);
                    works.add(subject.getMonhoc());
                }
            }
        }
        adapterWork = new AdapterWork(ThemLichCNActivity.this,dsCV);
        listWord.setAdapter(adapterWork);
        listWord.setLayoutManager(new LinearLayoutManager(ThemLichCNActivity.this));
        listWork = new ArrayAdapter<>(
                ThemLichCNActivity.this,
                android.R.layout.simple_list_item_1,
                works
        );
        chude.setThreshold(1);
        chude.setAdapter(listWork);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}