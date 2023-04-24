package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.cureit_healthassistant.R;

import java.util.Objects;

public class AboutUs extends AppCompatActivity {
    Toolbar aboutUsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutUsToolbar = findViewById(R.id.aboutUs_toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        TextView website = findViewById(R.id.linkOfDevelopersWebsite);

        website.setOnClickListener(v->{
            String url = "https://apoorva1823000.github.io/PortfolioWebsite/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

//        Toolbar settings
        toolbarTitle.setText(R.string.about_us);
        setSupportActionBar(aboutUsToolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
        aboutUsToolbar.setNavigationOnClickListener(v->onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}