package com.example.asignment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asignment.ClickListener;
import com.example.asignment.OnFragmentManager;
import com.example.asignment.R;
import com.example.asignment.adapter.AdapterPosst;
import com.example.asignment.model.Post;
import com.example.asignment.modelCategory.Category;
import com.example.asignment.modelPosst2.Post2;
import com.example.asignment.server.Retrofit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostFragment extends Fragment  {
    private TextView textView;
    private RecyclerView rv_Post;
    public List<Post2> posts;
    private AdapterPosst adapterPosst;
    private List<String> links;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frangment_post, container, false);
        rv_Post = view.findViewById(R.id.rv_post);
        posts = new ArrayList<>();
        links = new ArrayList<>();

        Bundle bundle = getArguments();
        int ID = bundle.getInt("ID");
        posts.size();
        Retrofit.getRetrofit().getposst(ID).enqueue(new Callback<Post2>() {
            @Override
            public void onResponse(Call<Post2> call, Response<Post2> response) {
                String Image = response.body().getContent().getRendered();
                Pattern pattern = Pattern.compile("asian.dotplays.com/wp-content/uploads(?<url>.*?)jpg");
                Matcher matcher = pattern.matcher(Image);

                while (matcher.find()) {

                    links.add("http://asian.dotplays.com/wp-content/uploads" + matcher.group(1) + "jpg");

                    Log.e("image", String.valueOf(links.size()));
                }
                adapterPosst = new AdapterPosst(links, getActivity(), new ClickListener() {
                    @Override
                    public void onClick(int position) {
                        imageDetailFragment fragment = new imageDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("link", links.get(position));
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
                        transaction.commit();
                    }
                });
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                rv_Post.setLayoutManager(gridLayoutManager);
                rv_Post.setHasFixedSize(true);
                rv_Post.setAdapter(adapterPosst);

            }

            @Override
            public void onFailure(Call<Post2> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }



}
