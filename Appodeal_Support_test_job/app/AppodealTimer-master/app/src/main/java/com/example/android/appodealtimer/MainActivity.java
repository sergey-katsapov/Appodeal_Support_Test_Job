package com.appodeal.support.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity   {
    private static final long START_TIME_IN_MILLIS = 30000; // 30sec.(1000 = 1sec.)
    private static final int TIME_FOR_BANNER = 5; // 5sec.
    private static final String APPODEAL_KEY = "489cf70b8e12bee7fdb75c180abc1f88b03aa266e54047f6";

    private TextView secondsTextView;
    private Button cancelBtn;
    private Button listBtn;

    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private int mStartTimeInSec = (int) (START_TIME_IN_MILLIS/1000);
    private int mTimeForBanner = TIME_FOR_BANNER;
    private boolean showAd = true;
    private boolean startTimer = false;

    private List<State> states = new ArrayList();
    private ListView countriesList;
    private StateAdapter stateAdapter;

    NativeAdViewContentStream nav_cs;
    NativeAd nativeAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initAppodeal();
        loadNative();


        setInitialData();

        countriesList = findViewById(R.id.countriesList);

        stateAdapter = new StateAdapter(this, states);

        countriesList.setAdapter(stateAdapter);

        listBtn = findViewById(R.id.show_list_view);

        secondsTextView = findViewById(R.id.seconds);
        cancelBtn = findViewById(R.id.cancel_ad);


        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countriesList.getVisibility() == View.GONE){
                    countriesList.setVisibility(View.VISIBLE);
                } else {
                    countriesList.setVisibility(View.GONE);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelShowingAd();
            }
        });

    }

    // Helper method to start timer.
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL) & showAd){
                    Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
                } else if(!showAd){
                    resetTimer();
                }
            }
        }.start();
    }

    // Helper method to update the timer.
    private void updateCountDownText(){
        int seconds = (int) (mTimeLeftInMillis  / 1000);
        if (mStartTimeInSec - seconds == mTimeForBanner){
            Appodeal.hide(MainActivity.this ,Appodeal.BANNER);
        }
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",  seconds);
        secondsTextView.setText(timeLeftFormatted);
    }

    // Helper method to reset the timer.
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        startTimer();
    }

    // Helper method to load NATIVE Appodeal
    private void loadNative(){
        Appodeal.cache(MainActivity.this, Appodeal.NATIVE);
        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {

            }

            @Override
            public void onNativeFailedToLoad() {
                Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {
                Toast.makeText(MainActivity.this, "SHOWN", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {
            }
        });

    }
    // Helper method showing NATIVE AD
    /*private void showNative(){
        nativeAd = Appodeal.getNativeAds(1).get(0);
        nav_cs = findViewById(R.id.native_ad_view_content_stream);
        nav_cs.setNativeAd(nativeAd);
    }*/

    // Helper method to init Appodeal
    private void initAppodeal(){
        Appodeal.setTesting(true);
        Appodeal.setAutoCache(Appodeal.NATIVE, false);
        Appodeal.initialize(this, APPODEAL_KEY,
                    Appodeal.INTERSTITIAL |
                            Appodeal.BANNER |
                             Appodeal.NATIVE);

        Appodeal.show(MainActivity.this, Appodeal.BANNER_TOP);

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean b) {

            }

            @Override
            public void onInterstitialFailedToLoad() {

            }

            @Override
            public void onInterstitialShown() {

            }

            @Override
            public void onInterstitialClicked() {

            }

            @Override
            public void onInterstitialClosed() {
                resetTimer();
            }
        });

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {

            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                startTimer();
            }

            @Override
            public void onBannerClicked() {

            }
        });
    }

    // Helper method to cancel showing ad.
    private void cancelShowingAd(){
        showAd = false;
    }

    // Preparing data to list view.
    private void setInitialData(){
        states.add(new State ("Brazil", "Brasilia", R.drawable.brazil));
        states.add(new State ("Argentina", "Buenos Aires", R.drawable.argentina));
        states.add(new State("Portugal", "Lisbon", R.drawable.portugal));
        states.add(new State ("Spain", "Madrid", R.drawable.spain));
        states.add(new State ("England", "London", R.drawable.england));
    }

}
