package com.example.cureit_healthassistant.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cureit_healthassistant.Adapters.ViewPagerAdapter;
import com.example.cureit_healthassistant.MainActivity;
import com.example.cureit_healthassistant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OnBoardingScreen extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout mDotLayout;
    Button backButton,nextButton,skipButton;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    FirebaseAuth boardAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        boardAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0)>0){
                    slideViewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0)<3){
                    slideViewPager.setCurrentItem(getItem(1),true);
                }else{
                    FirebaseUser currentUser = boardAuth.getCurrentUser();
                    if (currentUser!=null){
                        startActivity(new Intent(OnBoardingScreen.this, MainActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(OnBoardingScreen.this, Register.class));
                        finish();
                    }
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = boardAuth.getCurrentUser();
                if (currentUser!=null){
                    startActivity(new Intent(OnBoardingScreen.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(OnBoardingScreen.this, Register.class));
                    finish();
                }
            }
        });

        slideViewPager = (ViewPager)findViewById(R.id.slideViewPage);
        mDotLayout = (LinearLayout)findViewById(R.id.indicatorLayout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        slideViewPager.setAdapter(viewPagerAdapter);
        setUpIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);
        backButton.setVisibility(View.INVISIBLE);
    }

    public void setUpIndicator(int position){
        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.background,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.white,getApplicationContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
            if (position>0){
                backButton.setVisibility(View.VISIBLE);
            }else{
                backButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getItem(int i){
        return slideViewPager.getCurrentItem()+i;
    }
}