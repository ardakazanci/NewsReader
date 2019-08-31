package com.ardakazanci.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardakazanci.newsreader.Common.ISO8601DateParser;
import com.ardakazanci.newsreader.DetailActivity;
import com.ardakazanci.newsreader.Interface.IItemClickListener;
import com.ardakazanci.newsreader.Model.News.Article;
import com.ardakazanci.newsreader.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    IItemClickListener iItemClickListener;

    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;

    public ListNewsViewHolder(View itemView) {
        super(itemView);
        article_image = itemView.findViewById(R.id.article_image);
        article_time = itemView.findViewById(R.id.article_time);
        article_title = itemView.findViewById(R.id.article_title);

        itemView.setOnClickListener(this);

    }

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }


    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(itemView, getAdapterPosition(), false);
    }


}


public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<Article> articleList;
    private Context context;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }


    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_news_layout, parent, false);
        return new ListNewsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {
        // Picasso.get().load(response.body().getIcons().get(0).getUrl()).into(holder.source_image);
        Picasso.get().load(articleList.get(position).getUrlToImage()).into(holder.article_image);
        if (articleList.get(position).getTitle().length() > 65)
            holder.article_title.setText(articleList.get(position).getTitle().substring(0, 65) + "...");
        else
            holder.article_title.setText(articleList.get(position).getTitle());

        Date date = null;
        try {
            date = ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
        } catch (ParseException e) {
            e.getErrorOffset();
            e.printStackTrace();
        }

        holder.article_time.setReferenceTime(date.getTime());

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("webURL", articleList.get(position).getUrl());
                context.startActivity(detailIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
