package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser==null){
                startActivity(new Intent(SplashScreen.this,Register.class));
                finish();
            }
            else if (mAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }else{
                startActivity(new Intent(SplashScreen.this, LogIn.class));
                finish();
            }
        },2500);
    }
}