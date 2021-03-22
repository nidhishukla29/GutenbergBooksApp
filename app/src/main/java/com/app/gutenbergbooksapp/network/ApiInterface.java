package com.app.gutenbergbooksapp.network;

import com.app.gutenbergbooksapp.model.BookDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("books")
    Call <BookDataModel> getTopicWiseBookList(@Query("topic") String topic, @Query("page") int page);

    @GET("books")
    Call <BookDataModel> getSearchWiseBookList(@Query("search") String search, @Query("topic") String topic);


}
