package com.example.gunlukhaberapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gunlukhaberapp.Adapters.FavAdapter;
import com.example.gunlukhaberapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class FavoriteFragment extends Fragment {
    ListView list;
    DatabaseReference databaseFavorites;
    FirebaseAuth mAuth;
    String key;
    ArrayList<String>titleFb;
    ArrayList<String>photoFb;
    ArrayList<String>idFb;
    ArrayList<String>dateFb;
    ArrayList<String>descriptionFb;
    ArrayList<String>urlFb;
    FavAdapter favAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void getData(){
        databaseFavorites=FirebaseDatabase.getInstance().getReference("favorites").child(mAuth.getUid());
        databaseFavorites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()
                ) {
                    @SuppressWarnings("unchecked")
                    HashMap <String,String>hashMap= (HashMap<String, String>) ds.getValue();
                    System.out.println("Data Control"+ ds.getValue());
                    titleFb.add(hashMap.get("title"));
                    photoFb.add(hashMap.get("photo"));
                    idFb.add(hashMap.get("id"));
                    dateFb.add(hashMap.get("date"));
                    descriptionFb.add(hashMap.get("description"));
                    urlFb.add(hashMap.get("url"));
                    favAdapter=new FavAdapter(getActivity().getApplicationContext(),titleFb,idFb,photoFb,descriptionFb,dateFb,urlFb);
                    list.setAdapter(favAdapter);
                    favAdapter.notifyDataSetChanged();
                    System.out.println("Date"+hashMap.get("date"));
                    System.out.println("Title3"+ds.child("title").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.favorite_fragment,container,false);
        list=(ListView) view.findViewById(R.id.favList);
        mAuth = FirebaseAuth.getInstance();
        databaseFavorites=FirebaseDatabase.getInstance().getReference().child("fav");
        key = databaseFavorites.push().getKey();
        titleFb= new ArrayList<String>();
        photoFb= new ArrayList<String>();
        idFb= new ArrayList<String>();
        dateFb= new ArrayList<String>();
        descriptionFb= new ArrayList<String>();
        urlFb=new ArrayList<String>();
        getData();

        return view;
    }
}
