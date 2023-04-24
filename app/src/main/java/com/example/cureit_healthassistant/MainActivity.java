package com.example.cureit_healthassistant;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cureit_healthassistant.Fragments.AppointmentFragment;
import com.example.cureit_healthassistant.Fragments.DummyMed;
import com.example.cureit_healthassistant.Fragments.HomeFragment;
import com.example.cureit_healthassistant.Fragments.MedicineFragment;
import com.example.cureit_healthassistant.Fragments.ProfilesFragment;
import com.example.cureit_healthassistant.Screens.AboutUs;
import com.example.cureit_healthassistant.Screens.LogIn;
import com.example.cureit_healthassistant.Screens.OnBoardingScreen;
import com.example.cureit_healthassistant.Screens.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    String[] address = {"apoorva.cse1@gmail.com"};
    final String emergencyCall = "108";
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadPermissionGranted = false;
    private boolean isCallPermissionGranted = false;
    private boolean isLocationPermissionGranted = false;
    private boolean isRecordPermissionGranted = false;
    private boolean isCameraPermissionGranted = false;

    String FamilyDrCall;

    Dialog dialog;
    DrawerLayout drawerLayout;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        floatingActionButton = findViewById(R.id.fab);
        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId()==R.id.home){
                replaceFragment(new HomeFragment());
            }else if (item.getItemId()==R.id.medicines){
                replaceFragment(new MedicineFragment());
            }else if (item.getItemId()==R.id.appointment){
                replaceFragment(new AppointmentFragment());
            }else if (item.getItemId()==R.id.profile){
                replaceFragment(new ProfilesFragment());
            }else{
                Toast.makeText(MainActivity.this, "Error encountered with bottom navigation view", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

//        Set permissions management
        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                 if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) !=null){
                     isReadPermissionGranted = Boolean.TRUE.equals(result.get(Manifest.permission.READ_EXTERNAL_STORAGE));
                 }
                 if (result.get(Manifest.permission.CALL_PHONE) !=null){
                     isCallPermissionGranted = Boolean.TRUE.equals(result.get(Manifest.permission.CALL_PHONE));
                 }
                 if (result.get(Manifest.permission.ACCESS_FINE_LOCATION) !=null){
                     isLocationPermissionGranted = Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION));
                 }
                 if (result.get(Manifest.permission.RECORD_AUDIO) !=null){
                     isRecordPermissionGranted = Boolean.TRUE.equals(result.get(Manifest.permission.RECORD_AUDIO));
                 }
                 if (result.get(Manifest.permission.CAMERA) !=null){
                     isCameraPermissionGranted = Boolean.TRUE.equals(result.get(Manifest.permission.CAMERA));
                 }
        });
        requestPermission();

//        Custom dialog box
        dialog = new Dialog(this);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference2 = firebaseFirestore.collection("familyDoctor").document(firebaseAuth.getCurrentUser().getUid());
        documentReference2.addSnapshotListener((value, error) -> {
            assert value != null;
            FamilyDrCall = value.getString("DoctorPhoneNumber");
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation,R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_health_and_safety_24);
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        replaceFragment(new HomeFragment());


        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.nav_home){
                replaceFragment(new HomeFragment());
            }
//            else if (item.getItemId()==R.id.nav_track_parcel){
//                Toast.makeText(this, "Page to be made", Toast.LENGTH_SHORT).show();
//            }else if (item.getItemId()==R.id.nav_settings){
//                startActivity(new Intent(MainActivity.this, Settings.class));
//            }
            else if (item.getItemId()==R.id.nav_share){
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }else if (item.getItemId()==R.id.nav_about){
                startActivity(new Intent(MainActivity.this, AboutUs.class));
            }else if (item.getItemId()==R.id.nav_logout){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LogIn.class));
                finish();
            }else{
                Toast.makeText(this, "Version: "+BuildConfig.VERSION_CODE, Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

//        Floating action button
        floatingActionButton.setOnClickListener(v -> showBottomDialog());

    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        ConstraintLayout chatLayout = dialog.findViewById(R.id.chatLayout);
        ConstraintLayout vcLayout = dialog.findViewById(R.id.videoCallLayout);
        ConstraintLayout ChatBotLayout = dialog.findViewById(R.id.chatBotLayout);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        chatLayout.setOnClickListener(v -> {

            dialog.dismiss();
            Toast.makeText(MainActivity.this,"Chatting with an expert",Toast.LENGTH_SHORT).show();

        });

        vcLayout.setOnClickListener(v -> {

            dialog.dismiss();
            Toast.makeText(MainActivity.this,"Video conference booked with an expert",Toast.LENGTH_SHORT).show();

        });

        ChatBotLayout.setOnClickListener(v -> {

            dialog.dismiss();
            Toast.makeText(MainActivity.this,"No ChatBot available right now",Toast.LENGTH_SHORT).show();

        });

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.tutorial){
            startActivity(new Intent(MainActivity.this, OnBoardingScreen.class));
        }else if (item.getItemId()==R.id.emergency){
            makeEmergencyCall();
        }else if (item.getItemId()==R.id.problem){
            composeEmail(address,"Report a problem in CureIt");
        }else if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LogIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }else if (item.getItemId()==R.id.familyDr){
            makeFamilyDrCall();
        }
        return super.onOptionsItemSelected(item);
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(intent);
    }

    public void makeEmergencyCall(){
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+emergencyCall));//change the number
                        startActivity(callIntent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Call permission required for this. Kindly head to the device settings to turn it on", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
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
                        Toast.makeText(MainActivity.this, "Call permission required for this. Kindly head to the device settings to turn it on", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    private void requestPermission(){
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        isCallPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        isRecordPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        List<String> permissionRequest = new ArrayList<>();
        if (!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!isCallPermissionGranted){
            permissionRequest.add(Manifest.permission.CALL_PHONE);
        }
        if (!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA);
        }
        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!isRecordPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!permissionRequest.isEmpty()){
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //additional code
            dialog.setContentView(R.layout.dialog_design);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button yes = dialog.findViewById(R.id.dialogYesBtn);
            Button no = dialog.findViewById(R.id.dialogNoBtn);
            dialog.show();
            dialog.setCancelable(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dialog.getWindow().setBackgroundBlurRadius(30);
            }
            yes.setOnClickListener(v -> {
                dialog.cancel();
                finishAffinity();
                finish();
            });
            no.setOnClickListener(v -> {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "You can press yes to exit !", Toast.LENGTH_SHORT).show();
            });
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}