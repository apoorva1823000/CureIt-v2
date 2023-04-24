package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddDoctor extends AppCompatActivity {
    private TextInputEditText DrName,DrEmail,DrYOP,DrSpecialization,DrPhoneNumber;
    private RadioButton DrRadioMale,DrRadioFemale;
    private String DrSaveGender = "";
    private ProgressBar DrLoading;
    private FirebaseFirestore DrFirebaseFirestore;
    private FirebaseAuth DrFirebaseAuth;
    private Button DrSave;
    private DatabaseReference DrDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        DrName = findViewById(R.id.DrFullNameET);
        DrEmail = findViewById(R.id.DrEmailIDET);
        DrYOP = findViewById(R.id.DrYOPET);
        DrSpecialization = findViewById(R.id.DrSpecializationET);
        DrRadioMale = findViewById(R.id.DrMaleRadioButton);
        DrRadioFemale = findViewById(R.id.DrFemaleRadioButton);
        DrSave = findViewById(R.id.DrregistrationButton);
        DrPhoneNumber = findViewById(R.id.DrMblET);
        DrLoading = findViewById(R.id.DrloadingRegistration);
        DrFirebaseAuth = FirebaseAuth.getInstance();
        DrFirebaseFirestore = FirebaseFirestore.getInstance();
        DrDatabaseReference = FirebaseDatabase.getInstance().getReference("FamilyDoctor");
        DrSave.setOnClickListener(v -> {
            DrLoading.setVisibility(View.VISIBLE);
            String DoctorFullName = DrName.getText().toString().trim();
            String DoctorYOP = DrYOP.getText().toString().trim();
            String DoctorEmail = DrEmail.getText().toString().trim();
            String DoctorPhoneNumber = DrPhoneNumber.getText().toString().trim();
            String DoctorSpecialization = DrSpecialization.getText().toString().trim();
            if (DrRadioMale.isChecked()){
                DrSaveGender = "Male";
            }else{
                DrSaveGender = "Female";
            }
            if (!validateDrFullName(DoctorFullName)|!validateDrPhoneNumber(DoctorPhoneNumber)|!validateDrEmail(DoctorEmail)|!validateDrYOP(DoctorYOP)|!validateDrSpecialization(DoctorSpecialization)|(!DrRadioMale.isChecked()&&!DrRadioFemale.isChecked())){
                Toast.makeText(AddDoctor.this, "Data incomplete", Toast.LENGTH_SHORT).show();
                DrLoading.setVisibility(View.GONE);
            }
            else{
                Map<String, String> doctor = new HashMap<>();
                doctor.put("DoctorName",DoctorFullName);
                doctor.put("DoctorYOP",DoctorYOP);
                doctor.put("DoctorEmail",DoctorEmail);
                doctor.put("DoctorGender",DrSaveGender);
                doctor.put("DoctorSpecialization",DoctorSpecialization);
                doctor.put("DoctorPhoneNumber",DoctorPhoneNumber);
                DrFirebaseFirestore.collection("familyDoctor").document(DrFirebaseAuth.getCurrentUser().getUid())
                        .set(doctor)
                        .addOnSuccessListener(documentReference -> {
                            DrLoading.setVisibility(View.INVISIBLE);
                            Toast.makeText(this, "Doctor Details Saved Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddDoctor.this,MainActivity.class));
                            finish();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "Doctor Details Registration Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    private boolean validateDrFullName(String SaveDrFullName) {
        if (SaveDrFullName.isEmpty()){
            DrName.setError("Field cannot be empty");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            DrName.setError(null);
            DrLoading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateDrPhoneNumber(String SaveDrPhoneNumber) {
        if (SaveDrPhoneNumber.isEmpty()){
            DrPhoneNumber.setError("Field cannot be empty");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            DrPhoneNumber.setError(null);
            DrLoading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateDrYOP(String SaveDrYOP){
        if (SaveDrYOP.isEmpty()){
            DrYOP.setError("Field cannot be empty");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        } else if (Integer.parseInt(SaveDrYOP)<=5&&Integer.parseInt(SaveDrYOP)>=120) {
            DrYOP.setError("Kindly enter valid data");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            DrYOP.setError(null);
            DrLoading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateDrEmail(String SaveDrEmail){
        if (SaveDrEmail.isEmpty()){
            DrEmail.setError("Field cannot be empty");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(SaveDrEmail).matches()){
            DrEmail.setError("Please enter a valid email address");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            DrEmail.setError(null);
            DrLoading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private boolean validateDrSpecialization(String SaveDrSpecialization) {
        if (SaveDrSpecialization.isEmpty()){
            DrSpecialization.setError("Field cannot be empty");
            DrLoading.setVisibility(View.INVISIBLE);
            return false;
        }else{
            DrSpecialization.setError(null);
            DrLoading.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}