package com.example.gunlukhaberapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gunlukhaberapp.Adapters.NewsAdapter;
import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.News;
import com.example.gunlukhaberapp.R;
import com.example.gunlukhaberapp.Services.ApiClient;
import com.example.gunlukhaberapp.Services.NewsService.GetNewsServicce;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    GetNewsServicce apiService;
    News news=new News();
    ListView list;
    List<Article>articles=new ArrayList<Article>();
    Context context;
    NewsAdapter newsAdapter;
    SwipeRefreshLayout pullToRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment,container,false);
         list=(ListView) view.findViewById(R.id.newsList);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(this);
        apiService= ApiClient.getClient().create(GetNewsServicce.class);
        Call<News> call=apiService.getNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.d("Responses: ",new Gson().toJson(response.body()));
                news=response.body();
                articles=news.getArticles();
                newsAdapter=new NewsAdapter(getActivity(),articles);
                list.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();
                Log.d("Test",news.getStatus());
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }
        });

        return  view;
    }
    void refreshData(){
        apiService= ApiClient.getClient().create(GetNewsServicce.class);
        Call<News> call=apiService.getNews();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.d("Responses: ",new Gson().toJson(response.body()));
                news=response.body();
                articles=news.getArticles();
                newsAdapter=new NewsAdapter(getActivity(),articles);
                list.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }
        });
        pullToRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pullToRefresh.setRefreshing(true);
        refreshData();
    }
}
