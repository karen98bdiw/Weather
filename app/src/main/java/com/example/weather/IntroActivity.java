package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ImageView weatherKindIcon;
    private TextView weatherDescription;
    private Button skipBtn;
    private Gson gson;
    private OneDayWeather curentDayWeather;
    private List<OneDayWeather> allWeekWeather;
    private LayoutInflater inflater;
    private ViewPager viewPager;
    private List<View> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.introActivityViewPager);

        gson = new Gson();
        allWeekWeather = new ArrayList<>();
        pages = new ArrayList<>();

        File file = getFilesDir();
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".wt");
            }
        });

        if(files.length > 0){

            for(File f:files){
                StringBuilder sb = new StringBuilder();
                try {
                    FileInputStream fis = new FileInputStream(f);
                    InputStreamReader inp = new InputStreamReader(fis);
                    BufferedReader bf = new BufferedReader(inp);
                    String line;
                    while((line = bf.readLine()) != null){
                        sb.append(line);
                    }
                    OneDayWeather oneDayWeather = gson.fromJson(sb.toString(),OneDayWeather.class);
                    allWeekWeather.add(oneDayWeather);
                    Log.e("hey",allWeekWeather.size() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            curentDayWeather = allWeekWeather.get(0);
            drowIntro();

        }else{
            Intent intent = new Intent(IntroActivity.this,MainActivity.class);
            startActivity(intent);
        }



    }

    public void drowIntro(){
        inflater =  LayoutInflater.from(IntroActivity.this);
        View dayWeatherView = inflater.inflate(R.layout.app_intro_viewpager_item,null);
        weatherKindIcon = dayWeatherView.findViewById(R.id.introImage);
        StringBuilder sb = new StringBuilder();
        sb.append("http://openweathermap.org/img/w/")
                .append(curentDayWeather.getWeatherIcon())
                .append(".png");
        Log.e("iconU",sb.toString());
        Glide
                .with(IntroActivity.this)
                .load(sb.toString())
                .into(weatherKindIcon);
        weatherDescription = dayWeatherView.findViewById(R.id.introText);
        weatherDescription.setText("At day will be " + curentDayWeather.getWeatherKind());
        pages.add(dayWeatherView);

        inflater =  LayoutInflater.from(IntroActivity.this);
        View nightWeatherView = inflater.inflate(R.layout.app_intro_viewpager_item,null);
        weatherKindIcon = nightWeatherView.findViewById(R.id.introImage);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("http://openweathermap.org/img/w/")
                .append(curentDayWeather.getNightWeatherKind())
                .append(".png");
        Log.e("iconU",sb2.toString());
        Glide
                .with(IntroActivity.this)
                .load(sb.toString())
                .into(weatherKindIcon);
        weatherDescription = nightWeatherView.findViewById(R.id.introText);
        weatherDescription.setText("At the night will be " +curentDayWeather.getNightWeatherKind());
        pages.add(nightWeatherView);

        for(View v:pages){
            v.findViewById(R.id.skipBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        viewPager.setAdapter(new IntroViewPagerAdapter(pages));
        viewPager.setCurrentItem(0);
    }
}
