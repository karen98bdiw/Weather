package com.example.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

//    static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
//    static final String APPID = "9328aa88a8e3e7336e7ca0df235a226a";
//
//    private List<View> introPages;
//    private LayoutInflater inflater;
//    private ViewPager viewPager;
//    private Retrofit retrofit;
//    private Gson gson;
//    private CompositeDisposable compositeDisposable;
//    private View view;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//
//        gson = new Gson();
//
//        compositeDisposable = new CompositeDisposable();
//
//        introPages = new ArrayList<>();
//
//        viewPager = findViewById(R.id.introActivityViewPager);
//
//        inflater = LayoutInflater.from(this);
//
//         view = inflater.inflate(R.layout.app_intro_viewpager_item,null);
//
//
//         introPages.add(view);
//        viewPager.setCurrentItem(0);
//
//        viewPager.setAdapter(new IntroViewPagerAdapter(introPages));
//
//        retrofit = new Retrofit.Builder()
//                  .baseUrl(BASE_URL)
//                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                  .addConverterFactory(GsonConverterFactory.create(gson))
//                  .build();
//
//        final RetrofitRequestInterface requestInterface = retrofit.create(RetrofitRequestInterface.class);
//
//        requestInterface.getApiResponse("524901","9328aa88a8e3e7336e7ca0df235a226a")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        compositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onSuccess(final Response response) {
//
//                        view.findViewById(R.id.skipBtn).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
//                                intent.putExtra("ResultWeather",response);
//                                startActivity(intent);
//                            }
//                        });
//
//                        List<OneDayWeather> weekDaysWeather = takeWeatherInUsefullFormat(response);
//
//                        for (OneDayWeather o:weekDaysWeather){
//                            Log.e("rrr", "onSuccess: " + o.getMaxTemp() );
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("no","somethingiswrong");
//                        e.printStackTrace();
//                    }
//                });

    }


}
