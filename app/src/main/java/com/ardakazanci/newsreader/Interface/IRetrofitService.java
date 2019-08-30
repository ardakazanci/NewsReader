package com.ardakazanci.newsreader.Interface;

import com.ardakazanci.newsreader.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitService {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

}
