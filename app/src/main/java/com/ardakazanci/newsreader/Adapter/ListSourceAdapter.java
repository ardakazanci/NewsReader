package com.ardakazanci.newsreader.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardakazanci.newsreader.Common.Common;
import com.ardakazanci.newsreader.Interface.Favicon.IconBetterIdeaService;
import com.ardakazanci.newsreader.Interface.IItemClickListener;
import com.ardakazanci.newsreader.Model.Favicon.IconBetterIdea;
import com.ardakazanci.newsreader.Model.WebSite;
import com.ardakazanci.newsreader.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;
    private WebSite webSite;

    private IconBetterIdeaService mService;


    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        mService = Common.getIconBetterIdeaService();
    }



    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {

        StringBuilder iconBetterApi = new StringBuilder("http://icons.better-idea.org/allicons.json?url=");
        iconBetterApi.append(webSite.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterApi.toString())
                .enqueue(new Callback<IconBetterIdea>() {
                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                        if(response.body().getIcons().size() >0){
                            Picasso.get().load(response.body().getIcons().get(0).getUrl()).into(holder.source_image);

                        }
                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                        Log.e("ListSourceAdapter",""+t.getMessage());

                    }
                });

        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //
                Log.e("Tıklandı",""+position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}


class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    IItemClickListener iItemClickListener;

    TextView source_title;
    CircleImageView source_image;


    public ListSourceViewHolder(View itemView) {
        super(itemView);
        source_image = itemView.findViewById(R.id.source_image);
        source_title = itemView.findViewById(R.id.source_name);
    }

    // Adapter'den bu başlatılıyor.
    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }


    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
