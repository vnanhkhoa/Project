package com.vnak.sotaysinhvien.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnak.sotaysinhvien.R;
import com.vnak.sotaysinhvien.Task.UpdateUser;
import com.vnak.sotaysinhvien.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThongTinFragment extends Fragment {

    TextView txtBirth,txtClass,txtEmail;
    TextInputLayout txtName,txtSdt,txtDiaChi;
    RadioButton rbtnMale,rbtnFemale;
    Button btnLuu;
    SharedPreferences sharedPreferences;
    String msv = null,lop,email;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");

    public ThongTinFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin, container, false);
        txtBirth = view.findViewById(R.id.txtbirth);
        txtClass = view.findViewById(R.id.txtClass);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtName = view.findViewById(R.id.txtName);
        txtSdt = view.findViewById(R.id.txtSDT);
        txtDiaChi = view.findViewById(R.id.txtDiaChi);
        rbtnFemale = view.findViewById(R.id.rbtnFemale);
        rbtnMale = view.findViewById(R.id.rbtnMale);
        btnLuu = view.findViewById(R.id.btnLuu);
        addData();
        addEvents();
        return view;
    }

    private void addEvents() {
        txtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyDate(txtBirth);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!validete(txtName) | !validete(txtDiaChi)| !validete(txtSdt)){
                        return;
                    }
                    String name = txtName.getEditText().getText().toString().trim();
                    String gt = "";
                    if (rbtnMale.isChecked()) gt = rbtnMale.getText().toString();
                    if (rbtnFemale.isChecked()) gt = rbtnFemale.getText().toString();
                    String address = txtDiaChi.getEditText().getText().toString().trim();
                    String sdt = txtSdt.getEditText().getText().toString().trim();
                    String birth = new SimpleDateFormat("yyyy-MM-dd").format(spf.parse(txtBirth.getText().toString()));
                    User user = new User();
                    user.setMsv(msv);
                    user.setName(name);
                    user.setSdt(sdt);
                    user.setAddress(address);
                    user.setGender(gt);
                    user.setBirth(birth);
                    UpdateTask updateTask = new UpdateTask();
                    updateTask.execute(user);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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

    private void xulyDate(final TextView textView) {
        try {
            calendar.setTime(spf.parse(textView.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                textView.setText(spf.format(calendar.getTime()));
            }
        };
        DatePickerDialog date = new DatePickerDialog(
                getContext(),
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        date.show();
    }

    private void addData() {
        if (sharedPreferences != null) {
            msv = sharedPreferences.getString("MSV",null);
            String name = sharedPreferences.getString("Name",null);
            String gender = sharedPreferences.getString("Gender",null);
            String birth = sharedPreferences.getString("Birth",null);
            lop = sharedPreferences.getString("Class",null);
            email = sharedPreferences.getString("Email",null);
            String address = sharedPreferences.getString("Address",null);
            String sdt = sharedPreferences.getString("SDT",null);
            txtBirth.setText(birth);
            txtName.getEditText().setText(name);
            if (gender.equals("Nam")) rbtnMale.setChecked(true);
            if (gender.equals("Nữ")) rbtnFemale.setChecked(true);
            txtDiaChi.getEditText().setText(address);
            txtSdt.getEditText().setText(sdt);
            txtEmail.setText(email);
            txtClass.setText(lop);

        }
    }

    class UpdateTask extends UpdateUser {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean == true) {
                Toast.makeText(getContext(),"Lưu thông tin thành công",Toast.LENGTH_LONG).show();
                String gt = "";
                if (rbtnMale.isChecked()) gt = rbtnMale.getText().toString();
                if (rbtnFemale.isChecked()) gt = rbtnFemale.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Name",txtName.getEditText().getText().toString().trim());
                editor.putString("Birth",txtBirth.getText().toString().trim());
                editor.putString("Address",txtDiaChi.getEditText().getText().toString().trim());
                editor.putString("SDT",txtSdt.getEditText().getText().toString().trim());
                editor.putString("Gender",gt);
                editor.commit();
            } else {
                Toast.makeText(getContext(),"Lưu thông tin thất bại",Toast.LENGTH_LONG).show();
            }
        }
    }

}