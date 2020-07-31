package com.example.myapplication2.ui.categories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.CategoriesRecyclerAdapter;
import com.example.myapplication2.R;

import java.util.Arrays;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel categoriesViewModel;
    private RecyclerView categoriesRecyclerView;
    private Context context;
    private CategoriesRecyclerAdapter categoriesRecyclerAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoriesViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        categoriesRecyclerView = root.findViewById(R.id.categoriesRecyclerView);

        List<String> categoriesList = Arrays.asList(getResources().getStringArray(R.array.categories));
        categoriesRecyclerAdapter = new CategoriesRecyclerAdapter(getContext(), categoriesList);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriesRecyclerView.setAdapter(categoriesRecyclerAdapter);


        return root;


    }



}
