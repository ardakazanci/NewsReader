package com.ardakazanci.newsreader.Interface.Favicon;

import com.ardakazanci.newsreader.Model.Favicon.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconBetterIdeaService {

    @GET
    Call<IconBetterIdea> getIconUrl (@Url String url);

}
