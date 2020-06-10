package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.model.PhepTinh;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView txtKQ,txtNhap;
    Button btnMot,btnHai,btnBa,btnBon,btnNam,btnSau,btnBay,
            btnTam,btnChin,btnKhong,btnNhan,btnChia,btnCong,
            btnTru,btnXoa,btnCham,btnAC,btnPhanTram,btnCongTru,btnBang;

    PhepTinh phepTinh;
    private static float sizeText;
    private int doDai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEnvents();
    }

    private void addControls() {
        txtKQ = findViewById(R.id.txtKQ);
        txtNhap = findViewById(R.id.txtNhap);
        btnMot = findViewById(R.id.btnMot);
        btnHai = findViewById(R.id.btnHai);
        btnBa = findViewById(R.id.btnBa);
        btnBon = findViewById(R.id.btnBon);
        btnNam = findViewById(R.id.btnNam);
        btnSau = findViewById(R.id.btnSau);
        btnBay = findViewById(R.id.btnBay);
        btnTam = findViewById(R.id.btnTam);
        btnChin = findViewById(R.id.btnChin);
        btnKhong = findViewById(R.id.btnKhong);
        btnCong = findViewById(R.id.btnCong);
        btnTru = findViewById(R.id.btnTru);
        btnChia = findViewById(R.id.btnChia);
        btnNhan = findViewById(R.id.btnNhan);
        btnXoa = findViewById(R.id.btnXoa);
        btnCham = findViewById(R.id.btnCham);
        btnAC = findViewById(R.id.btnAC);
        btnPhanTram = findViewById(R.id.btnPhanTram);
        btnCongTru = findViewById(R.id.btnCongTru);
        btnBang = findViewById(R.id.btnBang);

        phepTinh = new PhepTinh();
        sizeText =   txtNhap.getTextSize();
    }

    private void addEnvents() {
        btnMot.setOnClickListener(this);
        btnHai.setOnClickListener(this);
        btnBa.setOnClickListener(this);
        btnBon.setOnClickListener(this);
        btnNam.setOnClickListener(this);
        btnSau.setOnClickListener(this);
        btnBay.setOnClickListener(this);
        btnTam.setOnClickListener(this);
        btnChin.setOnClickListener(this);
        btnKhong.setOnClickListener(this);
        btnCong.setOnClickListener(this);
        btnChia.setOnClickListener(this);
        btnNhan.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
        btnCham.setOnClickListener(this);
        btnAC.setOnClickListener(this);
        btnPhanTram.setOnClickListener(this);
        btnCongTru.setOnClickListener(this);
        btnBang.setOnClickListener(this);
        btnTru.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnMot:
                txtNhap.setText(txtNhap.getText().toString()+this.btnMot.getText());
                break;
            case R.id.btnHai:
                txtNhap.setText(txtNhap.getText().toString()+this.btnHai.getText());
                break;
            case R.id.btnBa:
                txtNhap.setText(txtNhap.getText().toString()+this.btnBa.getText());
                break;
            case R.id.btnBon:
                txtNhap.setText(txtNhap.getText().toString()+this.btnBon.getText());
                break;
            case R.id.btnNam:
                txtNhap.setText(txtNhap.getText().toString()+this.btnNam.getText());
                break;
            case R.id.btnSau:
                txtNhap.setText(txtNhap.getText().toString()+this.btnSau.getText());
                break;
            case R.id.btnBay:
                txtNhap.setText(txtNhap.getText().toString()+this.btnBay.getText());
                break;
            case R.id.btnTam:
                txtNhap.setText(txtNhap.getText().toString()+this.btnTam.getText());
                break;
            case R.id.btnChin:
                txtNhap.setText(txtNhap.getText().toString()+this.btnChin.getText());
                break;
            case R.id.btnKhong:
                txtNhap.setText(txtNhap.getText().toString()+this.btnKhong.getText());
                break;
            case R.id.btnCham:
                txtNhap.setText(txtNhap.getText().toString()+this.btnCham.getText());
                break;
            case R.id.btnXoa:
                break;
            case R.id.btnCong:
                txtNhap.setText(txtNhap.getText().toString()+this.btnCong.getText());
                break;
            case R.id.btnTru:
                txtNhap.setText(txtNhap.getText().toString()+this.btnTru.getText());
                break;
            case R.id.btnNhan:
                txtNhap.setText(txtNhap.getText().toString()+this.btnNhan.getText());
                break;
            case R.id.btnChia:
                txtNhap.setText(txtNhap.getText().toString()+this.btnChia.getText());
                break;
            case R.id.btnAC:
                txtNhap.setText("");
                txtNhap.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeText);
                break;
            case R.id.btnCongTru:
//                txtNhap.setText(txtNhap.getText().toString()+this.btnCongTru.getText());
                break;
            case R.id.btnBang:
                catChuoi();
                break;
            case R.id.btnPhanTram:
                txtNhap.setText(txtNhap.getText().toString()+this.btnPhanTram.getText());
                break;
        }
        chechFont();

    }

    private void catChuoi() {
        String txt = txtNhap.getText().toString();
        String nhan = txt.replace(btnNhan.getText().toString(),"*");
        String chia = nhan.replace(btnChia.getText().toString(),"/");
        String phanTram = chia.replace(btnPhanTram.getText().toString(),"/100");
        Expression expression = new ExpressionBuilder(phanTram).build();
        try {
            double result = expression.evaluate();
            txtKQ.setText(Double.toString(result));
        } catch (ArithmeticException ex) {
            txtNhap.setText("Error");
        }
    }

    private void chechFont() {
        int leng = new StringBuilder(txtNhap.getText().toString()).length();
        float size = txtNhap.getTextSize();

        switch (leng)
        {
            case 10:
                txtNhap.setTextSize(TypedValue.COMPLEX_UNIT_PX,size - 50);
                break;
            case 15:
                txtNhap.setTextSize(TypedValue.COMPLEX_UNIT_PX,size - 20);

                break;
            case 20:
                txtNhap.setTextSize(TypedValue.COMPLEX_UNIT_PX,size - 20);

                break;
            case 25:
                txtNhap.setTextSize(TypedValue.COMPLEX_UNIT_PX,size - 10);
                break;
        }
    }
}
