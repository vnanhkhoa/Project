package com.sict.drink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    EditText txtEmail,txtPass;
    Button btnSignUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                if (TextUtils.isEmpty(email) | TextUtils.isEmpty(pass)) {
                    if (TextUtils.isEmpty(email)) {
                        txtEmail.setError(txtEmail.getHint()+" can't be empty");
                    }
                    if (TextUtils.isEmpty(pass)) {
                        txtPass.setError(txtPass.getHint()+" can't be empty");
                    }
                    return;
                }

                signUp(email,pass);
            }
        });
    }

    private void signUp(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Tạo Tài Khoản Thành Công",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                            System.out.println(task.getException().getMessage().toString());
                        }
                    }
                });
    }

    private void addControls() {
        txtEmail = findViewById(R.id.txttaikhoan);
        txtPass = findViewById(R.id.txtmatkhau);
        btnSignUp = findViewById(R.id.btnregister);
    }
}