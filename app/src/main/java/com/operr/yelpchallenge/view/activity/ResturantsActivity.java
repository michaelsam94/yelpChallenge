package com.operr.yelpchallenge.view.activity;


import android.os.Bundle;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.controller.backend.RequestObserver;
import com.operr.yelpchallenge.controller.UserManger;
import com.operr.yelpchallenge.controller.backend.GetAuthClientOperation;
import com.operr.yelpchallenge.model.Client;
import com.operr.yelpchallenge.model.Consts;

import butterknife.ButterKnife;

public class ResturantsActivity extends BaseActivity implements RequestObserver{



    private GetAuthClientOperation mGetAuthClientOperation;

    public ResturantsActivity() {
        super(R.layout.activity_resturants, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        getClientAuthFromApi();
    }

    private void getClientAuthFromApi() {
        mGetAuthClientOperation = new GetAuthClientOperation(UserManger.getInstance().getClient(),
                Consts.GET_AUTH_CLIENT_OP,true,this);
        mGetAuthClientOperation.addRequsetObserver(this);
        mGetAuthClientOperation.execute();
    }




    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resulObject) {
        if(requestId.equals(Consts.GET_AUTH_CLIENT_OP)){
            if(error != null){

            } else {

            }
        }
    }
}
