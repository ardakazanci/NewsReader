package com.ardakazanci.newsreader.Common;

import com.ardakazanci.newsreader.Interface.Favicon.IconBetterIdeaService;
import com.ardakazanci.newsreader.Interface.IRetrofitService;
import com.ardakazanci.newsreader.Remote.IconClient;
import com.ardakazanci.newsreader.Remote.RetrofitClient;

public class Common {

    public static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "e7822f1f8abc4ff4ba7aa78ca88a6bc6";


    public static IRetrofitService getRetrofitService() {
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitService.class);
    }

    public static IconBetterIdeaService getIconBetterIdeaService() {
        return IconClient.getClient().create(IconBetterIdeaService.class);
    }


}
