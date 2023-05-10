package com.dwm.a2.Service;

import com.dwm.a2.Utils.Constants;
import com.kwabenaberko.newsapilib.NewsApiClient;

public class NewsApiClientWrapper {
    private static NewsApiClient client;

    private NewsApiClientWrapper(){
    }

    public static NewsApiClient getInstance(){
        if(client == null){
            client = new NewsApiClient(Constants.API_KEY_ALTERNATE);
        }
        return client;
    }
}
