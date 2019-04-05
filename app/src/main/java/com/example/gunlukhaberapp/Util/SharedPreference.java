package com.example.gunlukhaberapp.Util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.News;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference extends Application {
    public static final String PREFS_NAME = "NEWS_APP";
    public static final String FAVORITES = "News_Favorite";
    private static SharedPreference mInstance;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private static Context mContext;


    public SharedPreference(){
        super();
    }

    @Override
    public void onCreate() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mContext = this;
        mInstance = this;
        sharedPreferences = this.getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onCreate();
    }

    public void saveFavorites(Context context, List<Article> favorites){

     Gson gson=new Gson();
     String jsonFavorites=gson.toJson(favorites);

     editor.putString(FAVORITES,jsonFavorites);
     editor.commit();
    }
    public void removeFavorite(Context context, Article news) {
        ArrayList<Article> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(news);
            saveFavorites(context, favorites);
        }
    }
    public void addFavorite(Context context, Article news) {
        List<Article> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Article>();
        favorites.add(news);
        saveFavorites(context, favorites);
    }
    public ArrayList<Article> getFavorites(Context context) {
        SharedPreferences settings;
        List<Article> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Article[] favoriteItems = gson.fromJson(jsonFavorites,
                    Article[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Article>(favorites);
        } else
            return null;

        return (ArrayList<Article>) favorites;
    }
}
