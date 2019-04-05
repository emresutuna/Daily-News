package com.example.gunlukhaberapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class CategoryDetailActivity extends AppCompatActivity {
String url;
String category;
InterstitialAd mInterstitialAd;
Handler handler;
Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        url = getIntent().getStringExtra("url");
        category=getIntent().getStringExtra("category");

        mInterstitialAd = new InterstitialAd(getApplicationContext());

        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        handler=new Handler(Looper.getMainLooper());
        runnable=new Runnable() {
            @Override
            public void run() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
            }
        };
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();

        }else {


            WebView webview = (WebView) findViewById(R.id.webView);


            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl(url);
            final ProgressDialog progress = ProgressDialog.show(this, "Lütfen Bekleyiniz", "Yükleniyor....", true);

            progress.show();
            webview.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    progress.dismiss();
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getApplicationContext(), "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            });
        }
        }

        public void displayInstestial(){
        handler.postDelayed(runnable,1);
        }

}
