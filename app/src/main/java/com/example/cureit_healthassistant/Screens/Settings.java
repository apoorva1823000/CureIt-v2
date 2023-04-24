package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;

import java.util.Objects;

public class Settings extends AppCompatActivity {
    Toolbar settingsToolbar;
    private TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsToolbar = findViewById(R.id.settings_toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(R.string.settings);
        setSupportActionBar(settingsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
        settingsToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}