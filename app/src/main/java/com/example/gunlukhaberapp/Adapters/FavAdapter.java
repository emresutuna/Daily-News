package com.example.gunlukhaberapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gunlukhaberapp.CategoryDetailActivity;
import com.example.gunlukhaberapp.Models.Favorite;
import com.example.gunlukhaberapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends ArrayAdapter<String> {
    ArrayList<String> titleList;
    ArrayList<String>idList;
    ArrayList<String>photoList;
    ArrayList<String>descriptionList;
    ArrayList<String>dateList;
    ArrayList<String>urlList;
    Context context;
    FirebaseAuth mAuth;
    String key;
    DatabaseReference databaseReferenceFavorite;
    public FavAdapter(Context context, ArrayList<String> title, ArrayList<String> id, ArrayList<String> photo, ArrayList<String> description, ArrayList<String> date,ArrayList<String>url) {
        super(context, R.layout.news_row,title);
        this.titleList = title;
        this.idList = id;
        this.photoList = photo;
        this.descriptionList = description;
        this.dateList = date;
        this.context = context;
        this.urlList=url;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.fav_rows,parent,false);
        final ToggleButton favorite=(ToggleButton)view.findViewById(R.id.favorite);
        CardView cardView=(CardView)view.findViewById(R.id.card_view);
        TextView title=(TextView)view.findViewById(R.id.title);
        TextView date=(TextView)view.findViewById(R.id.dateText);
        TextView content=(TextView)view.findViewById(R.id.content);
        TextView source=(TextView)view.findViewById(R.id.source);
        ImageView img=(ImageView)view.findViewById(R.id.newImage);
        ImageView cancel=(ImageView)view.findViewById(R.id.unFav);
        title.setText(titleList.get(position));
        Picasso.get().load(photoList.get(position)).into(img);
        content.setText(descriptionList.get(position));
        date.setText(dateList.get(position));
        mAuth = FirebaseAuth.getInstance();
        databaseReferenceFavorite = FirebaseDatabase.getInstance().getReference("favorites");
        key = databaseReferenceFavorite.push().getKey();
cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context, CategoryDetailActivity.class);
        intent.putExtra("url",urlList.get(position));
        context.startActivity(intent);
    }
});
cancel.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        Log.d("Cancel","cancelClicked");
        Log.d("Key",key);
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("favorites").child(mAuth.getUid()).child(idList.get(position));
dR.removeValue(new DatabaseReference.CompletionListener() {
    @Override
    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

    }
});


    }
});


        return view;
    }
}
