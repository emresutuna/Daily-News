package com.example.gunlukhaberapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gunlukhaberapp.CategoryDetailActivity;
import com.example.gunlukhaberapp.MainActivity;
import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.Favorite;
import com.example.gunlukhaberapp.Models.News;
import com.example.gunlukhaberapp.NewsActivity;
import com.example.gunlukhaberapp.R;
import com.example.gunlukhaberapp.Util.SharedPreference;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter  implements View.OnClickListener  {
    private Context mContext;
    private List<Article>articleListt=new ArrayList<>();
    LayoutInflater layoutInflater;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    SharedPreference sharedPreference;
    DatabaseReference  databaseReferenceFavorite;
    String key;
    SharedPreferences sharedPref;
    int counter=0;
    Boolean checked=false;
    private InterstitialAd mInterstitialAd;
    public  List <Favorite>favorites=new ArrayList<>();
    public NewsAdapter(Context context, List<Article> articleList){
        mContext=context;
        articleListt=articleList;
        sharedPreference=new SharedPreference();
        this.layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return articleListt.size();
    }

    @Override
    public Object getItem(int position) {
        return articleListt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(mContext).inflate(R.layout.news_row,parent,false);
        final Article article=articleListt.get(position);
         final ToggleButton favorite=(ToggleButton)convertView.findViewById(R.id.favorite);
        CardView cardView=(CardView)convertView.findViewById(R.id.card_view);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        TextView content=(TextView)convertView.findViewById(R.id.content);
        TextView source=(TextView)convertView.findViewById(R.id.source);
        ImageView img=(ImageView)convertView.findViewById(R.id.newImage);
        title.setText(article.getTitle());
        content.setText(article.getDescription());
        source.setText(article.getAuthor());
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        Picasso.get().load(article.getUrlToImage())

                .into(img);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fsdf","fsdfs");
                Log.d("Instersitial","Insterstieal test");
                counter++;
                Log.d("Counter",String.valueOf(counter));
                if(counter==5){

                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }else
                            {
                                Intent intent=new Intent(mContext,CategoryDetailActivity.class);
                                intent.putExtra("url",article.getUrl());
                                mContext.startActivity(intent);
                            }
                        }
                        @Override
                        public void onAdClosed() {
                            Intent intent=new Intent(mContext,CategoryDetailActivity.class);
                            intent.putExtra("url",article.getUrl());
                            mContext.startActivity(intent);
                        }
                    });
                    counter=0;
                }else{
                    Intent intent=new Intent(mContext,CategoryDetailActivity.class);
                    intent.putExtra("url",article.getUrl());
                    mContext.startActivity(intent);
                }




            }
        });

        database= FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Favoriler");
        mAuth = FirebaseAuth.getInstance();
        databaseReferenceFavorite = FirebaseDatabase.getInstance().getReference("favorites");

        String id=mAuth.getUid().toString();
      favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){

                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.favorite));
                    Toast.makeText(mContext, "Favorilere Eklenmiştir", Toast.LENGTH_SHORT).show();
                    key = databaseReferenceFavorite.push().getKey();
                    databaseReferenceFavorite.child(mAuth.getUid()).child(key).setValue(new Favorite(key,article.getUrlToImage(),article.getTitle(),article.getDescription(),article.getPublishedAt(),article.getUrl()));

                    Log.d("favChech",favorites.toString());
                }
                else if(isChecked==false){

                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.heart));


                    Log.d("aa","cancel");

                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("favorites").child(mAuth.getUid()).child(key);
                    dR.removeValue();

                }

            }
        });

        return convertView;
    }


    @Override
    public void onClick(View v) {
        Log.d("Emre","emre");
    }
    public boolean checkFavoriteItem(Article checkProduct) {
        boolean check = false;
        List<Article> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            for (Article product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    public void add(Article article,int position) {
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("ArticleFav",articleListt.get(position).getTitle());

    }

    public void remove(Article product,int position) {
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.remove("ArticleFav");


    }
}
