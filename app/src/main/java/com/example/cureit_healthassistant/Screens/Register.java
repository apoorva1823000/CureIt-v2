package com.example.cureit_healthassistant.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cureit_healthassistant.Helpers.Users;
import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    TextView goToLogin;
    TextInputEditText userName,fullName,age,email,password,confirmPassword;
    Button register;
    RadioButton radioMale,radioFemale;
    String SaveGender = "";
    ProgressBar loading;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{6,}" +
            "$");
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Casting Views
        userName = findViewById(R.id.UNameET);
        fullName = findViewById(R.id.FNameET);
        age = findViewById(R.id.AgeET);
        email = findViewById(R.id.EmailET);
        password = findViewById(R.id.PasswordET);
        confirmPassword = findViewById(R.id.ConfirmPasswordET);
        register = findViewById(R.id.registrationButton);
        radioMale = findViewById(R.id.MaleRadioButton);
        radioFemale = findViewById(R.id.FemaleRadioButton);
        loading = findViewById(R.id.loadingRegistration);

//        Working with the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

//        Register Button
        register.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            String SaveUserName = "@"+userName.getText().toString().trim().toLowerCase();
            String SaveFullName = fullName.getText().toString().trim();
            String saveAge = age.getText().toString().trim();
            String SaveEmail = email.getText().toString().trim();
            String SavePassword = password.getText().toString().trim();
            String SaveProfilePic = "";
            firebaseFirestore = FirebaseFirestore.getInstance();
            if (radioMale.isChecked()){
                SaveGender = "Male";
            }else{
                SaveGender = "Female";
            }
            if (!validateUserName(SaveUserName)|!validateName(SaveFullName)|!validateEmail(SaveEmail)|!validatePassword(SavePassword)|!validateAge(saveAge)|(!radioMale.isChecked()&&!radioFemale.isChecked())){
                Toast.makeText(Register.this, "Data incomplete", Toast.LENGTH_SHORT).show();
            }else{
                String SaveAge = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(saveAge));
                firebaseAuth.createUserWithEmailAndPassword(SaveEmail,SavePassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task12 -> Toast.makeText(this, "Please do verify your email id", Toast.LENGTH_SHORT).show());
//                                Users userInfo = new Users(
//                                        SaveUserName,
//                                        SaveFullName,
//                                        SaveAge,
//                                        SaveEmail,
//                                        SaveGender,
//                                        SaveProfilePic
//                                );
//                                FirebaseDatabase.getInstance().getReference("Users")
//                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
//                                        .setValue(userInfo)
//                                        .addOnCompleteListener(task1 -> {
//                                            Toast.makeText(this, "Registration Completed", Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(Register.this, MainActivity.class));
//                                            finish();
//                                        });
                                Map<String, String> user = new HashMap<>();
                                user.put("UserName",SaveUserName);
                                user.put("FullName",SaveFullName);
                                user.put("Age",SaveAge);
                                user.put("Email",SaveEmail);
                                user.put("Gender",SaveGender);
                                user.put("ProfilePic",SaveProfilePic);
                                firebaseFirestore.collection("user").document(firebaseAuth.getCurrentUser().getUid())
                                        .set(user)
                                        .addOnSuccessListener(documentReference -> {
                                            loading.setVisibility(View.INVISIBLE);
                                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Register.this,AddDoctor.class));
                                            finish();
                                        }).addOnFailureListener(e -> {
                                            Toast.makeText(this, "Registration Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

//        Go To Login
        goToLogin = findViewById(R.id.RegistrationLogin);
        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(Register.this,LogIn.class)); finish();
        });

    }

    private boolean validateUserName(String SaveUserName) {
        if (SaveUserName.isEmpty()){
            userName.setError("Field cannot be empty");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            userName.setError(null);
            loading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateEmail(String SaveEmail){
        if (SaveEmail.isEmpty()){
            email.setError("Field cannot be empty");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(SaveEmail).matches()){
            email.setError("Please enter a valid email address");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            email.setError(null);
            loading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validatePassword(String SavePassword){
        if (SavePassword.isEmpty()){
            password.setError("Field cannot be empty");
            loading.setVisibility(View.INVISIBLE);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(SavePassword).matches()) {
            password.setError("Password too weak. Must be at least six characters long alphanumeric and must have a special character");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else if (!Objects.requireNonNull(confirmPassword.getText()).toString().trim().equals(Objects.requireNonNull(password.getText()).toString().trim())){
            confirmPassword.setError("Passwords do not match");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }
        else{
            password.setError(null);
            loading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateAge(String SaveAge){
        if (SaveAge.isEmpty()){
            age.setError("Field cannot be empty");
            loading.setVisibility(View.INVISIBLE);
            return false;
        } else if (Integer.parseInt(SaveAge)<=5&&Integer.parseInt(SaveAge)>=120) {
            age.setError("Kindly enter valid data");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            age.setError(null);
            loading.setVisibility(View.INVISIBLE);
            return true;
        }
    }
    
    private boolean validateName(String SaveFullName){
        if (SaveFullName.isEmpty()){
            fullName.setError("Field cannot be empty");
            loading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            fullName.setError(null);
            loading.setVisibility(View.INVISIBLE);
            return true;
        }
    }
}