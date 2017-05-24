package com.operr.yelpchallenge.controller.backend;


import com.operr.yelpchallenge.model.AuthClient;
import com.operr.yelpchallenge.model.Client;
import com.operr.yelpchallenge.model.Resturant;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("https://api.yelp.com/oauth2/token")
    Call<AuthClient> getAuthClient(@Field("client_id") String clinetId,
                                   @Field("client_secret") String clientSecret);


    @GET(ApiClient.REQUESTS_BASE_URL )
    Call<Resturant> getResturant(@HeaderMap Map<String, String> headers,
                                  @Query("term") String term,
                                  @Query("location") String location);



}
