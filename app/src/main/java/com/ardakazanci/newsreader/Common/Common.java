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

    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a7072d9c2ad9495a8dd5cb58a7fd30df
    public static String getAPIUrl(String source, String sortBy, String apiKEY) {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }



}
