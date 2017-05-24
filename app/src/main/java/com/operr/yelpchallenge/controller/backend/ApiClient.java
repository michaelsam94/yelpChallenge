package com.operr.yelpchallenge.controller.backend;




import com.operr.yelpchallenge.common.helper.Logger;
import com.operr.yelpchallenge.common.helper.Utilities;
import com.operr.yelpchallenge.common.view.BaseApplication;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String REQUESTS_BASE_URL = "https://api.yelp.com/v3/businesses/search/";

    public static String token;


    public static HashMap<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        if(token != null) headers.put("Authorization", "Bearer " + token);
        return headers;
    }



    /*
     ********************************************************
     ************* Initialize Retrofit **********************
     ********************************************************
     */
    private static final long CACHE_SIZE = 20 * 1024 * 1024; // 10 MB
    private static OkHttpClient.Builder clientBuilder;
    static {
        clientBuilder = new OkHttpClient
                .Builder()
                .connectTimeout(80, TimeUnit.SECONDS)
                .readTimeout(80, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        okhttp3.Headers headers = request.headers();

                        Logger.instance().v("Request Headers", (headers != null) ? headers.toString() : "Null headers");
                        Logger.instance().v("Request URL", request.url());

                        Logger.instance().v("Request Body", (request.body() != null) ? bodyToString(request.body()) : "NULL/Empty");
                        Logger.instance().v("Response Body",(response.body() != null) ? response.body().string() : "NULL/Empty");


                        if (Utilities.isConnected(BaseApplication.getApplication())) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 50000).build();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                        }
                        return chain.proceed(request);
                    }
                });
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }


    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if(retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(REQUESTS_BASE_URL)
                    .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }
    // End of initializing Retrofit ////////////////////////
}