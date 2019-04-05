package com.example.gunlukhaberapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gunlukhaberapp.CategoryDetailActivity;
import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.Favorite;
import com.example.gunlukhaberapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private List<Favorite> favoriteList=new ArrayList<>();
    LayoutInflater layoutInflater;
    public  List <Favorite>favorites=new ArrayList<>();
    public FavoriteAdapter(Context context, List<Favorite> favoriteListt){
        mContext=context;
        favoriteList=favoriteListt;
        this.layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(mContext).inflate(R.layout.news_row,parent,false);
        final Favorite favorites=favoriteList.get(position);
        final ToggleButton favorite=(ToggleButton)convertView.findViewById(R.id.favorite);
        CardView cardView=(CardView)convertView.findViewById(R.id.card_view);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        TextView content=(TextView)convertView.findViewById(R.id.content);
        TextView source=(TextView)convertView.findViewById(R.id.source);
        ImageView img=(ImageView)convertView.findViewById(R.id.newImage);
        title.setText(favorites.getTitle());
        content.setText(favorites.getDescription());
        source.setText(favorites.getDate());
        Picasso.get().load(favorites.getPhoto())

                .into(img);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fsdf","fsdfs");
                Intent intent=new Intent(mContext, CategoryDetailActivity.class);
               // intent.putExtra("url",favorites.());
                mContext.startActivity(intent);
            }
        });


        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.heart));
                    favoriteList.clear();

                    Log.d("favChech",favorites.toString());
                }
                else{
                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.favorite));
                    favoriteList.clear();
                }

            }
        });

        return convertView;
    }


    @Override
    public void onClick(View v) {
        Log.d("Emre","emre");
    }
}
