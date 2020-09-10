package com.vnak.sotaysinhvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnak.sotaysinhvien.adapter.AdapterWorkCT;
import com.vnak.sotaysinhvien.model.CalendarSubject;
import com.vnak.sotaysinhvien.model.Work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditWorkActivity extends AppCompatActivity {

    CalendarSubject work;
    ArrayList<CalendarSubject> listWork;
    Calendar timer = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
    public static TextInputLayout txtChuDe,txtDiaChi;
    public static TextView txtGioBD,txtGioKT;
    Button btnSuaLich;
    public static Spinner thu;
    String[] dsThu;
    ArrayAdapter<String> adapterThu;
    RecyclerView listWord;
    AdapterWorkCT workCT;
    public static int positionWork;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        setTitle("Sửa Lịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        addControls();
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

        btnSuaLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuDe = txtChuDe.getEditText().getText().toString().trim();
                String bd = txtGioBD.getText().toString();
                String kt = txtGioKT.getText().toString();
                if (!chuDe.equals("") && bd.equals("") && kt.equals("") && txtDiaChi.getEditText().getText().equals("")) {
                    listWork.get(position).setMonhoc(chuDe);
                    Toast.makeText(getApplicationContext(), "Sửa chủ đề thành công", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validete(txtDiaChi) | !validete(txtChuDe)){
                    return;
                }
                xuLySuaLich();
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        SharedPreferences preferences = getSharedPreferences("Work", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String json = gson.toJson(listWork);
        System.out.println("WORK " + json);
        editor.putString("work",json);
        editor.commit();
    }

    private void xuLySuaLich() {
        String date = (!thu.getSelectedItem().equals("Chủ Nhật")) ? "T"+((String) thu.getSelectedItem()).substring(4) : "CN";
        String chuDe = txtChuDe.getEditText().getText().toString().trim();
        String diaChi = txtDiaChi.getEditText().getText().toString().trim();
        String thoiGian = txtGioBD.getText().toString()+"->"+txtGioKT.getText().toString();

        if (checkCalendarLike(date,chuDe,diaChi,thoiGian)) return;
        CalendarSubject CV = listWork.get(position);
        CV.setMonhoc(chuDe);
        Work w = CV.getDsWork().get(positionWork);
        w.setThoiGian(date);
        w.setGio(thoiGian);
        w.setDiaDiem(diaChi);
        workCT.notifyDataSetChanged();
        txtGioBD.setText("");
        txtGioKT.setText("");
        txtDiaChi.getEditText().setText("");
        Toast.makeText(getApplicationContext(), "Sửa lịch thành công", Toast.LENGTH_SHORT).show();
    }

    public boolean checkCalendarLike(String date,String chuDe,String diaChi,String time) {

        for (CalendarSubject cv : listWork) {
            int i = 0;
            for (Work w : cv.getDsWork()) {
                if (cv.getMonhoc().equals(work.getMonhoc())) {
                    if (i != positionWork) {
                        if (w.getDiaDiem().equals(diaChi)
                                && w.getGio().equals(time) && w.getThoiGian().equals(date)
                                || w.getGio().equals(time) && w.getThoiGian().equals(date)) {
                            Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                } else {
                    if (w.getDiaDiem().equals(diaChi)
                            && w.getGio().equals(time) && w.getThoiGian().equals(date)
                            || w.getGio().equals(time) && w.getThoiGian().equals(date)) {
                        Toast.makeText(getApplicationContext(), "Lịch Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                i++;
            }
        }
        return false;
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

    private void addControls() {
        txtChuDe = findViewById(R.id.txtChuDe);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtGioBD = findViewById(R.id.txtGioBD);
        txtGioKT = findViewById(R.id.txtGioKT);
        btnSuaLich = findViewById(R.id.btnSuaLich);
        thu = findViewById(R.id.spnThu);
        listWord = findViewById(R.id.listWord);
        dsThu = getResources().getStringArray(R.array.Date);
        adapterThu = new ArrayAdapter<>(EditWorkActivity.this,android.R.layout.simple_list_item_1,dsThu);
        thu.setAdapter(adapterThu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        position = getIntent().getIntExtra("Position",0);
        SharedPreferences preferences = getSharedPreferences("Work", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("work",null);

        listWork = gson.fromJson(json, new TypeToken<ArrayList<CalendarSubject>>(){}.getType());
        work = listWork.get(position);

        txtChuDe.getEditText().setText(work.getMonhoc());
        workCT = new AdapterWorkCT(EditWorkActivity.this,listWork.get(position).getDsWork());
        listWord.setLayoutManager(new LinearLayoutManager(EditWorkActivity.this));
        listWord.setAdapter(workCT);
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
                    EditWorkActivity.this,
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
}