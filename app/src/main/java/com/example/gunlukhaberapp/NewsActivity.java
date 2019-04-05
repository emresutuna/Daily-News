package com.example.gunlukhaberapp;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gunlukhaberapp.Fragments.CategoryFragment;
import com.example.gunlukhaberapp.Fragments.FavoriteFragment;
import com.example.gunlukhaberapp.Fragments.HomeFragment;
import com.example.gunlukhaberapp.Services.HttpService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class NewsActivity extends AppCompatActivity {
    private ActionBar toolbar;
    private Fragment fragment;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);
        BottomNavigationView navigation = findViewById(R.id.navigationView);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        toolbar = getSupportActionBar();

        toolbar.setTitle("Manşetler");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        loadFragment(new HomeFragment());
        Intent intent=new Intent(NewsActivity.this, HttpService.class);
        startService(intent);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

serviceIsRunning();
serviceController();


    }
public boolean serviceIsRunning(){
    ActivityManager serviceAdmin=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : serviceAdmin.getRunningServices(Integer.MAX_VALUE)){
        if (getApplicationContext().getPackageName().equals(service.service.getPackageName())){
            Log.d("Service is Running","true");
            return true;
        }
    }
    return false;
}
public void serviceController(){
        if (serviceIsRunning()){
            startService(new Intent(NewsActivity.this,HttpService.class));
        }else{

            stopService(new Intent(NewsActivity.this,HttpService.class));
        }
}

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Manşetler");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_category:
                    toolbar.setTitle("Kategoriler");
                    fragment = new CategoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_favorite:
                    toolbar.setTitle("Favoriler");
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


