package com.example.cureit_healthassistant.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LogIn extends AppCompatActivity {
    TextView goToRegistration,reset;
    TextInputEditText email,password;
    Button login;
    private FirebaseAuth mAuth;
    ProgressBar loadingLogin;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



//        Casting Views
        email = findViewById(R.id.LogInEmailET);
        password = findViewById(R.id.LogInPasswordET);
        login = findViewById(R.id.LogInButton);
        reset = findViewById(R.id.PasswordResetLogin);
        loadingLogin = findViewById(R.id.loadingLogin);


//        Working with authentication
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        login.setOnClickListener(v -> {
            loadingLogin.setVisibility(View.VISIBLE);
            String SaveEmail = email.getText().toString().trim();
            String SavePassword = password.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(SaveEmail,SavePassword)
                    .addOnCompleteListener(LogIn.this, task -> {
                        if (task.isSuccessful()) {
                            Objects.requireNonNull(mAuth.getCurrentUser()).reload();
                            if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()){
                                DocumentReference documentReference = firebaseFirestore.collection("user").document(mAuth.getCurrentUser().getUid());
                                documentReference.addSnapshotListener((value, error) -> {
                                    loadingLogin.setVisibility(View.GONE);
                                    startActivity(new Intent(LogIn.this,MainActivity.class));
                                    assert value != null;
                                    String username = value.getString("UserName");
                                    Toast.makeText(getApplicationContext(), "Welcome dear user "+username, Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                            }else{
                                loadingLogin.setVisibility(View.GONE);
                                Toast.makeText(this, "First kindly verify your email ID", Toast.LENGTH_SHORT).show();
                                mAuth.getCurrentUser().sendEmailVerification();
                            }
                            // Sign in success, update UI with the signed-in user's information


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Authentication Failed"+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            loadingLogin.setVisibility(View.GONE);
                        }
                    });
        });

//        Reset Password
        reset.setOnClickListener(v -> startActivity(new Intent(LogIn.this,ForgetPassword.class)));

//        Revert to Registration Page
        goToRegistration = findViewById(R.id.LogInRegistration);
        goToRegistration.setOnClickListener(v -> {
            startActivity(new Intent(LogIn.this,Register.class));finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}