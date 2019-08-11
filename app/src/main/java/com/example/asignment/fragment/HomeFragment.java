package com.example.asignment.fragment;

import android.app.usage.EventStats;
import android.content.Context;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.asignment.ClickListener;
import com.example.asignment.OnFragmentManager;
import com.example.asignment.R;

import com.example.asignment.adapter.HomeAdapter;
import com.example.asignment.model.Post;
import com.example.asignment.server.Retrofit;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ClickListener {
    private HomeAdapter homeAdapter;
    private List<Post> arrPosst;
    private RecyclerView rv_Home;

    private Post post;
    ClickListener listener;

    public void setOnClickItemTab1(ClickListener onClickItem) {
        this.listener = onClickItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frangment_home, container, false);
        rv_Home = view.findViewById(R.id.rv_home);
        arrPosst = new ArrayList<>();
        homeAdapter = new HomeAdapter(arrPosst, getActivity(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        rv_Home.setLayoutManager(gridLayoutManager);
        rv_Home.setHasFixedSize(true);
        rv_Home.setAdapter(homeAdapter);
        Retrofit.getRetrofit().getPostOfhome().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.e("data", response.body().size() + "");
                Log.e("datta", response.toString());
                List<Post> postList = response.body();
                homeAdapter.setData(postList);
                arrPosst.addAll(postList);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return view;
    }

    @Override
    public void onClick(int position) {
        post = new Post();
        post = arrPosst.get(position);
        int ID = post.getId();
        PostFragment fragment = new PostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", ID);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment).addToBackStack(null);
        transaction.commit();

       // Toast.makeText(getActivity(), "ok" + post.getId(), Toast.LENGTH_SHORT).show();
    }


}
