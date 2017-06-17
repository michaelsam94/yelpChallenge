package com.operr.yelpchallenge.view.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.controller.backend.RequestObserver;
import com.operr.yelpchallenge.controller.UserManger;
import com.operr.yelpchallenge.controller.backend.GetAuthClientOperation;
import com.operr.yelpchallenge.controller.backend.GetResturantsOperation;
import com.operr.yelpchallenge.model.Business;
import com.operr.yelpchallenge.model.Client;
import com.operr.yelpchallenge.model.Consts;
import com.operr.yelpchallenge.model.Coordinate;
import com.operr.yelpchallenge.model.ResturantsResponse;
import com.operr.yelpchallenge.view.adapter.ResturantsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.operr.yelpchallenge.model.Consts.GET_AUTH_CLIENT_OP;
import static com.operr.yelpchallenge.model.Consts.GET_RESTURANT_OP;

public class ResturantsActivity extends BaseActivity implements RequestObserver{



    private GetAuthClientOperation mGetAuthClientOperation;
    private GetResturantsOperation mGetResturantsOperation;

    private Coordinate coordinate;

    @BindView(R.id.rv_resturants)
    RecyclerView rvResturants;
    ResturantsAdapter mResturantsAdapter;
    List<Business> mBussinesses;


    public ResturantsActivity() {
        super(R.layout.activity_resturants, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        coordinate = new Coordinate(37.7670169511878,-122.42184275);
        startOperation(GET_AUTH_CLIENT_OP);
    }



    private void startOperation(String reqId){
        switch (reqId){
            case GET_AUTH_CLIENT_OP:
                mGetAuthClientOperation = new GetAuthClientOperation(UserManger.getInstance().getClient(),
                        GET_AUTH_CLIENT_OP,true,this);
                mGetAuthClientOperation.addRequsetObserver(this);
                mGetAuthClientOperation.execute();
                break;
            case GET_RESTURANT_OP:
                mGetResturantsOperation = new GetResturantsOperation(coordinate,GET_RESTURANT_OP,true,this);
                mGetResturantsOperation.addRequsetObserver(this);
                mGetResturantsOperation.execute();
                break;

        }
    }

    private void setUpRecylerView(){
        if(mBussinesses != null){
            mResturantsAdapter = new ResturantsAdapter(mBussinesses,this);
            rvResturants.setLayoutManager(new LinearLayoutManager(this));
            rvResturants.setAdapter(mResturantsAdapter);
        }
    }


    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resulObject) {
        if(requestId.equals(GET_AUTH_CLIENT_OP)){
            if(error != null){

            } else {
                startOperation(GET_RESTURANT_OP);
            }
        } else if(requestId.equals(GET_RESTURANT_OP)){
            if(error != null){

            } else {
                ResturantsResponse resturantsResponse = (ResturantsResponse) resulObject;
                mBussinesses = resturantsResponse.getBusinesses();
                setUpRecylerView();
            }
        }
    }
}
