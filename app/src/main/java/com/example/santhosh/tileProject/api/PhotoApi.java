package com.example.santhosh.tileProject.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoApi {

    private static Retrofit retrofit = null;

    private static final String ROOT_URL = "https://api.flickr.com/services/";

    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
            "api_key=949e98778755d1982f537d56236bbb42&tags=tile&" +
            "format=json&nojsoncallback=1&page=1&extras=url_t,url_l";

    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ROOT_URL)
                    .build();
        }
        return retrofit;
    }
}
