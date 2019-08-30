package com.ardakazanci.newsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.ardakazanci.newsreader.Adapter.ListSourceAdapter;
import com.ardakazanci.newsreader.Common.Common;
import com.ardakazanci.newsreader.Interface.IRetrofitService;
import com.ardakazanci.newsreader.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebSite;
    RecyclerView.LayoutManager layoutManager;
    IRetrofitService iRetrofitService;
    ListSourceAdapter adapter;
    AlertDialog alertDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebSiteSource(true);
            }
        });

        Paper.init(MainActivity.this);

        iRetrofitService = Common.getRetrofitService();

        listWebSite = findViewById(R.id.list_source);
        listWebSite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        listWebSite.setLayoutManager(layoutManager);

        alertDialog = new SpotsDialog.Builder().setContext(this).build();

        loadWebSiteSource(false);




    }

    private void loadWebSiteSource(boolean isRefreshed) {

        if(!isRefreshed){
            String cache = Paper.book().read("cache");
            if(cache != null && !cache.isEmpty()){
                WebSite webSite = new Gson().fromJson(cache,WebSite.class);
                adapter = new ListSourceAdapter(getBaseContext(),webSite);

                synchronized (adapter){
                    adapter.notifyAll();
                }


                listWebSite.setAdapter(adapter);
            }else{

                alertDialog.show();
                iRetrofitService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebSite.setAdapter(adapter);

                        Paper.book().write("cache",new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });

            }
        }else{
            alertDialog.show();
            iRetrofitService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter = new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();
                    listWebSite.setAdapter(adapter);

                    Paper.book().write("cache",new Gson().toJson(response.body()));
                    alertDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {
                    Log.e("MainActivity",""+t.getMessage());
                }
            });

        }

    }
}
