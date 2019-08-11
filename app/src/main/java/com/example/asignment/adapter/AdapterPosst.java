package com.example.asignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asignment.ClickListener;
import com.example.asignment.R;
import com.example.asignment.model.Post;
import com.example.asignment.modelPosst2.Post2;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterPosst extends RecyclerView.Adapter<AdapterPosst.ViewHolder> {
    private List<String> arrPosst2;
    private Context context;
    ClickListener clickListener;

    public AdapterPosst(List<String> arrPosst2, Context context, ClickListener clickListener) {
        this.arrPosst2 = arrPosst2;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AdapterPosst.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPosst.ViewHolder holder, final int position) {
//Post2 post=arrPosst2.get(position);
////        Pattern pattern=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
//        Pattern pattern=Pattern.compile("http://asian.dotplays.com/wp-content/upload(?<url>.*?).jpg");
//        Matcher matcher=pattern.matcher(post.getContent().getRendered());
//        List<String> tokens = new ArrayList<>();
//        while (matcher.find()){
//            String token ="http://asian.dotplays.com/wp-content/uploads"+matcher.group(1)+"jpg";
//            Log.e("token",token);
//            tokens.add(token);
//
//        }
//        Picasso.with(context).load(tokens.get(0)).into(holder.imageView);
//        Document document= Jsoup.parse(post.getContent().getRendered());
//        Elements elements=document.select("img");
//        Log.e("image",elements.get(0).attr("src"));
        Picasso.with(context).load(arrPosst2.get(position)).into(holder.imageView);
//        for (int i=0;i<=tokens.size();i++){
//            Log.e("token0",tokens.get());

//            tokens.clear();
//        }
holder.imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        clickListener.onClick(position);
    }
});

    }

    @Override
    public int getItemCount() {
        return arrPosst2.size();
    }

    public void setData(List<String> data) {
        this.arrPosst2 = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_avtPost);
        }
    }
}
