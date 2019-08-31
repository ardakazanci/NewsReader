package com.ardakazanci.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ardakazanci.newsreader.Adapter.ListNewsAdapter;
import com.ardakazanci.newsreader.Adapter.ListSourceAdapter;
import com.ardakazanci.newsreader.Common.Common;
import com.ardakazanci.newsreader.Interface.IRetrofitService;
import com.ardakazanci.newsreader.Model.News.Article;
import com.ardakazanci.newsreader.Model.News.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsActivity extends AppCompatActivity {

    KenBurnsView kbv;
    AlertDialog alertDialog;
    IRetrofitService mService;
    DiagonalLayout diagonalLayout;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source = "";
    String sortBy = "";
    String webHotURL = "";

    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        mService = Common.getRetrofitService();

        alertDialog = new SpotsDialog.Builder().setContext(this).build();

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source, true);
            }
        });

        diagonalLayout = findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detailIntent = new Intent(getBaseContext(), DetailActivity.class);
                detailIntent.putExtra("webURL", webHotURL);
                startActivity(detailIntent);

            }
        });
        kbv = findViewById(R.id.top_image);
        top_author = findViewById(R.id.top_author);
        top_title = findViewById(R.id.top_title);

        lstNews = findViewById(R.id.lstNews);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);


        if (getIntent() != null) {
            source = getIntent().getStringExtra("source");
            sortBy = getIntent().getStringExtra("sortBy");
            if (!source.isEmpty() && !sortBy.isEmpty()) {
                loadNews(source, false);
            }
        }

    }

    private void loadNews(String source, boolean isRefreshed) {

        if (!isRefreshed) {
            alertDialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            alertDialog.dismiss();
                            // First Article
                            Picasso.get().load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kbv);
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotURL = response.body().getArticles().get(0).getUrl();

                            List<Article> removeFirstItem = response.body().getArticles();
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });

        } else {

            alertDialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            alertDialog.dismiss();
                            // First Article
                            Picasso.get().load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kbv);
                            top_title.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotURL = response.body().getArticles().get(0).getUrl();

                            List<Article> removeFirstItem = response.body().getArticles();
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem, getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });


        }


    }
}
