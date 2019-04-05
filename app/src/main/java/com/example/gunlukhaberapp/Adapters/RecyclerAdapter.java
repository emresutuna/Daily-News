package com.example.gunlukhaberapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gunlukhaberapp.Models.Article;
import com.example.gunlukhaberapp.Models.CustomItemClickListener;
import com.example.gunlukhaberapp.Models.News;
import com.example.gunlukhaberapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Article> articleList=new ArrayList<>();
    CustomItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content, source;
        ToggleButton favorite;
        ImageView img;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            source = (TextView) view.findViewById(R.id.source);
            favorite=(ToggleButton)view.findViewById(R.id.favorite);
            img=(ImageView)view.findViewById(R.id.newImage);
        }
    }


    public RecyclerAdapter(List<Article> articleList,CustomItemClickListener listener) {
        this.articleList = articleList;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //listener.onItemClick(v,itemView.get);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Article article=articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getDescription());
        holder.source.setText(article.getAuthor());
        Picasso.get().load(article.getUrlToImage())

                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}