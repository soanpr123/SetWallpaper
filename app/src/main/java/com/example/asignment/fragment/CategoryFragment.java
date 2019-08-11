package com.example.asignment.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asignment.R;
import com.example.asignment.adapter.CategoryAdapter;
import com.example.asignment.adapter.HomeAdapter;
import com.example.asignment.model.Post;
import com.example.asignment.modelCategory.Category;
import com.example.asignment.server.Retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private RecyclerView rvCategory;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 1;
    private int per_page = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rvCategory = view.findViewById(R.id.rv_category);
        swipeRefreshLayout = view.findViewById(R.id.SwipeCate);
        Getdata(page,per_page);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                Getdata(page, per_page);
            }
        });
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList, getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCategory.setLayoutManager(gridLayoutManager);
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(categoryAdapter);


        return view;
    }

    private void Getdata(int page, int per_page) {
        Retrofit.getRetrofit().getCategorys(page, per_page).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> list = response.body();
                categoryAdapter.setData(list);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
