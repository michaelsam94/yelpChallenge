package com.operr.yelpchallenge.model;


import com.google.gson.annotations.SerializedName;

public class Business {
    private String id;
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("is_closed")
    private boolean isClosed;


}
