package com.example.gunlukhaberapp.Services.NewsService;

import com.example.gunlukhaberapp.Models.News;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetNewsServicce {
    @GET("top-headlines?country=tr&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News> getNews();
    @GET("top-headlines?country=tr&category=business&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getBusinessNew();
    @GET("top-headlines?country=tr&category=entertainment&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getMagazineNew();
    @GET("top-headlines?country=tr&category=health&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getMedicalNew();
    @GET("top-headlines?country=tr&category=science&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getScienceNew();
    @GET("top-headlines?country=tr&category=sports&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getSportNew();
    @GET("top-headlines?country=tr&category=technology&apiKey=e4b0e0346cd54056a4706de35f8546fe")
    Call<News>getTechNew();
}
