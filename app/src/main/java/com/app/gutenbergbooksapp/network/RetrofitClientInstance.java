package com.app.gutenbergbooksapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Use to create retrofit instance
 */
public class RetrofitClientInstance {
    Retrofit retrofit;
    private String mBaseUrl = "http://skunkworks.ignitesol.com:8000/";

    public Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

}
}
