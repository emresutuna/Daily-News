package com.example.gunlukhaberapp.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.News;
import com.example.gunlukhaberapp.NewsActivity;
import com.example.gunlukhaberapp.R;
import com.example.gunlukhaberapp.Services.NewsService.GetNewsServicce;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpService extends Service {
    GetNewsServicce apiService;
    Timer timer;
    Handler handler;
    List<Article> articleList;
    News news;
    Notification.Builder builder;
    Context context;
    String title ,content;
    static  long time=1800000;
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Service","Service on binding");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer=new Timer();
        handler=new Handler(Looper.getMainLooper());
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        },time,time);
    }

    public void getData(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                apiService= ApiClient.getClient().create(GetNewsServicce.class);
                Call<News> call=apiService.getNews();
                call.enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        Log.d("Service Response: ",new Gson().toJson(response.body()));
                        news=response.body();
                        articleList=news.getArticles();
                        title=new Gson().toJson(response.body().getArticles().get(0).getTitle());
                        content=new Gson().toJson(response.body().getArticles().get(0).getDescription());
                        Toast.makeText(HttpService.this, "Servis çalışıyor", Toast.LENGTH_LONG).show();
                        sendNotif();
                    }


                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });
            }
        });

}
public void sendNotif(){
    NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    Intent intent=new Intent(HttpService.this, NewsActivity.class);
    PendingIntent pending=PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);//Notificationa tıklanınca açılacak activityi belirliyoruz
    builder=new Notification.Builder(getApplicationContext())
            .setContentTitle(articleList.get(0).getTitle())
            .setContentText(articleList.get(0).getDescription())
            .setDefaults(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.busines);
    Notification not=builder.build();
    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(1,not);
    /*Notification notification;
    String tickerText=articleList.get(0).getContent();
    notification = new Notification(icon, tickerText, when);
    notification.flags |= Notification.FLAG_AUTO_CANCEL;//notificationa tıklanınca notificationın otomatik silinmesi için
    notification.defaults |= Notification.DEFAULT_SOUND;//notification geldiğinde bildirim sesi çalması için
    notification.defaults |= Notification.DEFAULT_VIBRATE;//notification geldiğinde bildirim titremesi için
    nm.notify(0, notification);*/
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
