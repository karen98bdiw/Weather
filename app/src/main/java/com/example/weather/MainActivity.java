package com.example.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weather.FiveDayForecastWeatherAPIResponseInterface.MyList;
import com.example.weather.FiveDayForecastWeatherAPIResponseInterface.Response;
import com.google.gson.Gson;

import org.reactivestreams.Subscription;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import greyfox.rxnetwork.RxNetwork;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    static final String APPID = "9328aa88a8e3e7336e7ca0df235a226a";
    private RetrofitRequestInterface requestInterface;

    private List<View> introPages;
    private LayoutInflater inflater;
    private ViewPager viewPager;
    private Retrofit retrofit;
    private Gson gson;
    private CompositeDisposable compositeDisposable;
    private RecyclerView recyclerView;
    private Button refreshBtn;
    private TextView curentDayWeatherKind;
    private TextView curentDayWeatherCount;
    private ImageView curentDayWeatherIcon;
    private TextView lastUpdateDate;
    private OneDayWeather gettedCurentDayWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        curentDayWeatherKind = findViewById(R.id.todaysWeatherKindView);
        curentDayWeatherCount = findViewById(R.id.todaysWeatherCountView);
        curentDayWeatherIcon = findViewById(R.id.todaysWeatherKindIconView);
        lastUpdateDate = findViewById(R.id.lastUpdateDate);


        recyclerView = findViewById(R.id.comingDaysWeatherRecyclerView);
        gson = new Gson();
        compositeDisposable = new CompositeDisposable();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        requestInterface = retrofit.create(RetrofitRequestInterface.class);

        refreshBtn = findViewById(R.id.refreshBtn);




        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProces();
            }
        });

        startProces();





    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }


    //convert api resonse into the thath format which i will use for save and show
    //algorithm will choose all forecast for one day and return the middle results for day
    public List<OneDayWeather> takeWeatherInUsefullFormat(Response repsonse){

        List<MyList> list = repsonse.getList();
        List<OneDayWeather> daysWeatherWithMiddleCounts = new ArrayList<>();
        String curentDay;
        int dayMaxTempMiddleCount = 0;
        int dayMinTempMiddleCount = 0;
        int increment = 0;
        int tool = 0;

        int equalByWeekDayName = 0;

              for (int i = equalByWeekDayName; i < list.size(); i++) {
                  dayMaxTempMiddleCount = 0;
                  dayMinTempMiddleCount = 0;
                  increment = 0;
                  Log.e("eq", "" + equalByWeekDayName);
                  curentDay = convertUnixToDayName(list.get(equalByWeekDayName).dt);
                  for (int j = i; j < list.size(); j++) {
                      if (convertUnixToDayName(list.get(j).dt).equals(curentDay)) {

                          increment++;
                          dayMaxTempMiddleCount += list.get(j).getMain().temp_max;
                          dayMinTempMiddleCount += list.get(j).getMain().temp_min;
                          equalByWeekDayName++;
                      }
                  }
                  if (increment > 0) {
                      dayMaxTempMiddleCount = dayMaxTempMiddleCount / increment;
                      dayMinTempMiddleCount = dayMaxTempMiddleCount / increment;
                  }

                  daysWeatherWithMiddleCounts.add(new OneDayWeather( convertUnixToDayName(list.get(equalByWeekDayName-1).dt),list.get(equalByWeekDayName-1).weather.get(0).getIcon(),list.get(equalByWeekDayName-1).getWeather().get(0).getDescription(), dayMinTempMiddleCount, dayMaxTempMiddleCount,list.get(0).getWeather().get(0).getIcon(),list.get(0).getWeather().get(0).getDescription()));
                  if(equalByWeekDayName >= list.size()){break;}
              }
              return daysWeatherWithMiddleCounts;

    }

    //simple function wich convert unix format date and return Week Day Name;
    public String convertUnixToDayName(int dt){
        Date date = new java.util.Date(dt*1000L);
        String d = new SimpleDateFormat("EE", Locale.ENGLISH).format(date);
        return d;
    }


    //call api request and get response about weather
public void getFiveDaysForecast(){
    Log.e("call", "getFiveDaysForecast: "+"iamcalled1" );
        requestInterface.getApiResponse("524901","9328aa88a8e3e7336e7ca0df235a226a")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response response) {
                        List<OneDayWeather> weekDaysWeather = takeWeatherInUsefullFormat(response);
                           saveWeatherInstance(weekDaysWeather);
                           showWeather(weekDaysWeather);

                        Log.e("call", "onSuccess: " + "iamcall3" );


                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("errpr", "onError: "+"errpr" );
                        Toast.makeText(MainActivity.this, "No conection,result cant update", Toast.LENGTH_SHORT).show();
                    }

                });
    }


    //show weather characters on screen
    public void showWeather(List<OneDayWeather> weekDaysWeather ){


        Log.e("call", "showWeather: " + "iamcall2" );


        OneDayWeather curentDayWeather = weekDaysWeather.get(0);
        curentDayWeatherCount.setText((curentDayWeather.getMaxTemp()+curentDayWeather.getMinTemp())/2+"");

        StringBuilder sb = new StringBuilder();
        sb.append("http://openweathermap.org/img/w/")
                .append(curentDayWeather.getWeatherIcon())
                .append(".png");
        Log.e("iconU",sb.toString());
        Glide
                .with(MainActivity.this)
                .load(sb.toString())
                .into(curentDayWeatherIcon);

        curentDayWeatherKind.setText(curentDayWeather.getWeatherKind()+"");


        weekDaysWeather.remove(curentDayWeather);


        lastUpdateDate.setText(new Date().toString());

        Log.e("showweather", "showWeather: "+"called" );

        ForecastDaysRecyclerAdapter adapter = new ForecastDaysRecyclerAdapter(weekDaysWeather,MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,5));
        adapter.notifyDataSetChanged();
        gettedCurentDayWeather = curentDayWeather;
    }

    //save apia result in iternal storage
    public void saveWeatherInstance(List<OneDayWeather> weatherForecast){
        int increment = 0;

        try {
            for(OneDayWeather o:weatherForecast){
                String writingWeather = gson.toJson(o);
                Log.e("saved", "saveWeatherInstance: " + writingWeather );
                File f = new File(MainActivity.this.getFilesDir(),"weather" + increment++ + ".wt");
                FileOutputStream fis = new FileOutputStream(f);
                fis.write(writingWeather.getBytes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load saved instance from internal storage
    public List<OneDayWeather> loadWeatherSavedInstance(){

        List<OneDayWeather> loadeWeatherList = new ArrayList<>();
        File dir = getFilesDir();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".wt");
            }
        });


        for(File f:files){
            StringBuilder sb = new StringBuilder();
            try {
                FileInputStream fis = new FileInputStream(f);
                InputStreamReader isreader = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(isreader);
                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line);

                }

                OneDayWeather weatherInstance = (OneDayWeather) gson.fromJson(sb.toString(),OneDayWeather.class);
                loadeWeatherList.add(weatherInstance);

                Log.e("loading", "loadWeatherSavedInstance: " +sb.toString() );

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return loadeWeatherList;
    }

    //check conection
    public boolean isConected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    //in this function application check the internet conection and decide update result or show saved instance
    public void startProces(){
        if(isConected()){
            Log.e("conect", "onCreate: " + true );
            getFiveDaysForecast();
            Toast.makeText(this, "Weather is updated", Toast.LENGTH_SHORT).show();
        }else{
            if(isHaveSavedInstance()){
                Log.e("conect", "onCreate: " + false );
                showWeather(loadWeatherSavedInstance());
                Toast.makeText(this, "No conection,data cant be updated", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No conection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //this function return me answer-is i already have saved weather instance
    public boolean isHaveSavedInstance(){
        File file = getFilesDir();
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".wt");


            }
        });

        if(files.length > 0){
            return true;
        }return false;
    }
}
