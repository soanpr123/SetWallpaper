package com.example.asignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerNonConfig;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asignment.ClickListener;
import com.example.asignment.OnFragmentManager;
import com.example.asignment.R;
import com.example.asignment.fragment.PostFragment;
import com.example.asignment.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Post> arrPosst;
    private Context context;
    OnFragmentManager manager;
    FragmentManager fragmentManager;
    ClickListener clickListener;
    List<String> tokens = new ArrayList<>();



//    public HomeAdapter(List<Post> arrPosst, Context context) {
//        this.arrPosst = arrPosst;
//        this.context = context;
//    }

    public HomeAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public HomeAdapter(List<Post> arrPosst, Context context, ClickListener clickListener) {
        this.arrPosst = arrPosst;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, final int position) {
        final Post post = arrPosst.get(position);
//        Glide.with(context).load(post.getContent().getRendered()).into(holder.img_Avt);
//        Pattern pattern=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
//        Matcher matcher=pattern.matcher(post.getContent().getRendered());
//        while (matcher.find()){
//            String token = matcher.group(1);
//            Log.e("token",token);
//            tokens.add(token);
//
//        }
        Picasso.with(context).load(post.getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails().getSizes().getMedium().getSourceUrl()).into(holder.img_Avt);
//        tokens.clear();

        holder.tv_tile.setText(post.getTitle().getRendered());
//        holder.layout.(new ClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(context, "ok" + post.getId(), Toast.LENGTH_SHORT).show();
//                Bundle bundle=new Bundle();
//                bundle.putInt("id",post.getId());
//                PostFragment postFragment=new PostFragment();
//                postFragment.setArguments(bundle);
//
//
//            }
//        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrPosst.size();
    }

    public void setData(List<Post> data) {
        this.arrPosst = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Avt;
        RelativeLayout layout;
        TextView tv_tile;
        ClickListener clickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Avt = itemView.findViewById(R.id.img_avtHome);
            tv_tile = itemView.findViewById(R.id.txtTile);
            layout = itemView.findViewById(R.id.rvLayout);
//            layout.setOnClickListener(this);
        }

//
//        public void setOnclick(ClickListener clickListener) {
//            this.clickListener = clickListener;
//        }
//
//        @Override
//        public void onClick(View view) {
//            clickListener.onClick(view, getAdapterPosition(), false);
//        }
    }
}
