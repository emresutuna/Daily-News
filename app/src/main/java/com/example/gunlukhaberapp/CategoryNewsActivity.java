package com.example.gunlukhaberapp;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.gunlukhaberapp.Adapters.NewsAdapter;
import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.News;
import com.example.gunlukhaberapp.Services.ApiClient;
import com.example.gunlukhaberapp.Services.NewsService.GetNewsServicce;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryNewsActivity extends AppCompatActivity {
String category;
    GetNewsServicce apiService;
    News news=new News();
    ListView list;
    List<Article> articles=new ArrayList<Article>();
    Context context;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_news);
        list=(ListView)findViewById(R.id.listView);

        category=getIntent().getStringExtra("category");
        if(category.equals("busines")){
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getBusinessNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    news=response.body();
                    articles=new ArrayList<Article>();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }else if(category.equals("magazine")){
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getMagazineNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    articles=new ArrayList<Article>();
                    news=response.body();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        } else if (category.equals("medical")) {
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getMedicalNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    articles=new ArrayList<Article>();
                    news=response.body();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }else if(category.equals("tech")){
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getTechNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    articles=new ArrayList<Article>();
                    news=response.body();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }else if(category.equals("science")){
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getScienceNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    articles=new ArrayList<Article>();
                    news=response.body();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }else if(category.equals("sport")){
            apiService= ApiClient.getClient().create(GetNewsServicce.class);
            Call<News> call=apiService.getSportNew();
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    Log.d("Responses: ",new Gson().toJson(response.body()));
                    articles=new ArrayList<Article>();
                    news=response.body();
                    articles=news.getArticles();

                    newsAdapter=new NewsAdapter(getApplicationContext(),articles);

                    list.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    Log.d("Test",news.getStatus());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }
    }
}
