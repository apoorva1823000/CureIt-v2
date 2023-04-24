package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cureit_healthassistant.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    TextView loginRevert;
    TextInputEditText ResetEmail;
    Button reset;
    private FirebaseAuth resetAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

//        Casting views
        loginRevert = findViewById(R.id.ResetLogIn);
        ResetEmail = findViewById(R.id.ResetEmailET);
        reset = findViewById(R.id.ResetButton);
        resetAuth = FirebaseAuth.getInstance();

//        Reset in progress
        reset.setOnClickListener(v -> {
            String ResetSavedEmail = Objects.requireNonNull(ResetEmail.getText()).toString().trim();
            if (ResetEmail.getText().toString().isEmpty()){
                Toast.makeText(this, "Email cannot be left empty", Toast.LENGTH_SHORT).show();
            }else{
                resetAuth.sendPasswordResetEmail(ResetSavedEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgetPassword.this, "Kindly check your email and spam", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPassword.this,LogIn.class));
                        finish();
                    }else{
                        Toast.makeText(ForgetPassword.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        Revert back to login
        loginRevert.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPassword.this,LogIn.class));
            finish();
        });
    }
}