package com.operr.yelpchallenge.controller.backend;


import android.content.Context;

import com.operr.yelpchallenge.common.controller.backend.BaseOperation;
import com.operr.yelpchallenge.model.Business;
import com.operr.yelpchallenge.model.Coordinate;
import com.operr.yelpchallenge.model.ResturantsResponse;

import java.util.List;

public class GetResturantsOperation extends BaseOperation<ResturantsResponse>{

    private Coordinate mCoordinate;

    public GetResturantsOperation(Coordinate coordinate, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.mCoordinate = coordinate;
    }


    @Override
    public ResturantsResponse doMain() throws Throwable {
        ResturantsResponse responseApi = OperationsManager.getInstance().getResturants(mCoordinate);
        return responseApi != null ? responseApi : null;
    }
}
