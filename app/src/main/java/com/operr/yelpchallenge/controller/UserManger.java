package com.operr.yelpchallenge.controller;


import com.bumptech.glide.util.ContentLengthInputStream;
import com.operr.yelpchallenge.model.AuthClient;
import com.operr.yelpchallenge.model.Client;
import com.operr.yelpchallenge.model.Consts;

public class UserManger {

    private static UserManger instance;

    private Client mClient;
    private AuthClient mAuthClient;

    private UserManger() {
    }

    public static UserManger getInstance() {
        if(instance == null) instance = new UserManger();
        return instance;
    }


    public Client getClient() {
        if(mClient == null){
            mClient = new Client();
            mClient.setClientId(Consts.CLIENT_ID);
            mClient.setClientSecret(Consts.CLIENT_SECRET);
        }
        return mClient;
    }



    public AuthClient getAuthClient() {
        return mAuthClient;
    }

    public void setAuthClient(AuthClient authClient) {
        this.mAuthClient = authClient;
    }
}
