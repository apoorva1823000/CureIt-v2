package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;


import com.example.cureit_healthassistant.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

public class CuresActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView h1,d1,h2,d2,h3,d3,h4,d4;
    private String FamilyDrCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cures);
        Toolbar toolbar = findViewById(R.id.collapsedtoolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitle(name);

        h1 = findViewById(R.id.heading1);
        h2 = findViewById(R.id.heading2);
        h3 = findViewById(R.id.heading3);
        h4 = findViewById(R.id.heading4);
        d1 = findViewById(R.id.data1);
        d2 = findViewById(R.id.data2);
        d3 = findViewById(R.id.data3);
        d4 = findViewById(R.id.data4);
        FloatingActionButton callDr = findViewById(R.id.contactDr);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("Cures").document(name);
        documentReference.addSnapshotListener((value, error) -> {
            assert value != null;
            d1.setText(value.getString("what"));
            d2.setText(value.getString("doctor"));
            d3.setText(value.getString("when"));
            d4.setText(value.getString("cure"));
        });
        DocumentReference documentReference2 = firebaseFirestore.collection("familyDoctor").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        documentReference2.addSnapshotListener((value, error) -> {
            assert value != null;
            FamilyDrCall = value.getString("DoctorPhoneNumber");
        });

        callDr.setOnClickListener(v -> makeFamilyDrCall());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void makeFamilyDrCall(){
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+FamilyDrCall));//change the number
                        startActivity(callIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(CuresActivity.this, "Call permission required for this. Kindly head to the device settings to turn it on", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}