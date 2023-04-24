package com.example.cureit_healthassistant.Adapters;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cureit_healthassistant.R;

import org.w3c.dom.Text;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int images[] = {com.example.cureit_healthassistant.R.drawable.onboard_one,com.example.cureit_healthassistant.R.drawable.onboard_two,com.example.cureit_healthassistant.R.drawable.onboard_three,com.example.cureit_healthassistant.R.drawable.onboard_four};
    int headings[] = {com.example.cureit_healthassistant.R.string.onboardTitleOne,com.example.cureit_healthassistant.R.string.onboardTitleTwo,com.example.cureit_healthassistant.R.string.onboardTitleThree,com.example.cureit_healthassistant.R.string.onboardTitleFour};
    int descriptions[] = {com.example.cureit_healthassistant.R.string.onboardDescriptionOne,com.example.cureit_healthassistant.R.string.onboardDescriptionTwo,com.example.cureit_healthassistant.R.string.onboardDescriptionThree,com.example.cureit_healthassistant.R.string.onboardDescriptionFour};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(com.example.cureit_healthassistant.R.layout.slider_layout,container,false);

        ImageView slideTitleImage = (ImageView)view.findViewById(com.example.cureit_healthassistant.R.id.titleImage);
        TextView slideHeading = (TextView)view.findViewById(com.example.cureit_healthassistant.R.id.textTitle);
        TextView slideDescription = (TextView)view.findViewById(com.example.cureit_healthassistant.R.id.textDescription);

        slideTitleImage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
