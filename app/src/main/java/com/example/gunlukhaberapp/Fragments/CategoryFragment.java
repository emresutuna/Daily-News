package com.example.gunlukhaberapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gunlukhaberapp.CategoryNewsActivity;
import com.example.gunlukhaberapp.Models.TransferData;
import com.example.gunlukhaberapp.R;

public class CategoryFragment extends Fragment {
    Fragment fragment;
    String category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.category_fragment,container,false);
        LinearLayout busines=(LinearLayout)view.findViewById(R.id.bussines);
        LinearLayout magazine=(LinearLayout)view.findViewById(R.id.magazine);
        LinearLayout medical=(LinearLayout)view.findViewById(R.id.medical);
        LinearLayout science=(LinearLayout)view.findViewById(R.id.science);
        LinearLayout tech=(LinearLayout)view.findViewById(R.id.tech);
        LinearLayout sport=(LinearLayout)view.findViewById(R.id.sport);

        busines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="busines";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        magazine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="magazine";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="medical";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="tech";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="science";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category="sport";
                fragment = new CategoryDetail(category);
                loadFragment(fragment);
            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
