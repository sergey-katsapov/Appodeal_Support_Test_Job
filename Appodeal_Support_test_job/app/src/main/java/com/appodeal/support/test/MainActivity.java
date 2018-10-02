package com.appodeal.support.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity   {

    private static final String APPODEAL_KEY = "489cf70b8e12bee7fdb75c180abc1f88b03aa266e54047f6";

    private TextView secondsOfTextView;
    private Button cancel;
    private Button listButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillisecond = 30000;
    private int startTimeInSecond = (int) (timeLeftInMillisecond/1000);
    private boolean showAd = true;
    private List<Network> networks = new ArrayList();
    private ListView iconList;
    private NetworkAdapter networkAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAppodeal();
        loadNative();
        setInitialData();

        iconList = findViewById(R.id.iconList);
        networkAdapter = new NetworkAdapter(this, networks);
        iconList.setAdapter(networkAdapter);
        listButton = findViewById(R.id.show_list_view);
        secondsOfTextView = findViewById(R.id.seconds);
        cancel = findViewById(R.id.cancel);


        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iconList.getVisibility() == View.GONE){
                    iconList.setVisibility(View.VISIBLE);
                } else {
                    iconList.setVisibility(View.GONE);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelShowingAd();
            }
        });
    }


    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillisecond, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillisecond = millisUntilFinished;
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

    private void updateCountDownText(){
        int seconds = (int) (timeLeftInMillisecond  / 1000);
        if (startTimeInSecond - seconds == 5){
            Appodeal.hide(MainActivity.this ,Appodeal.BANNER);
        }
        String timeFormat = String.format(Locale.getDefault(), "%02d",  seconds);
        secondsOfTextView.setText(timeFormat);
    }
    private void resetTimer() {
        timeLeftInMillisecond = 30000;
        startTimer();
    }



    //Load NATIVE
    private void loadNative(){
        Appodeal.cache(MainActivity.this, Appodeal.NATIVE);
        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {}

            @Override
            public void onNativeFailedToLoad() {
            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {
            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {}
        });

    }
    private void initAppodeal(){
        Appodeal.setTesting(true);
        Appodeal.setAutoCache(Appodeal.NATIVE, false);
        Appodeal.initialize(this, APPODEAL_KEY,
                    Appodeal.INTERSTITIAL |
                            Appodeal.BANNER |
                             Appodeal.NATIVE);

        Appodeal.show(MainActivity.this, Appodeal.BANNER_TOP);
        
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
    }
    private void cancelShowingAd(){
        showAd = false;
    }


    private void setInitialData(){
        networks.add(new Network("VK", "Pavel Durov", R.drawable.vk));
        networks.add(new Network("Facebook", "Mark Elliot Zuckerberg", R.drawable.facebook));
        networks.add(new Network("LinkedIN", "Reid Hoffman", R.drawable.linkedin));
        networks.add(new Network("Twitter", "Jack Dorsey", R.drawable.twitter));
    }

}
