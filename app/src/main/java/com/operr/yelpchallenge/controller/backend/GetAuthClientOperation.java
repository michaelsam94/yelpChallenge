package com.operr.yelpchallenge.controller.backend;


import android.content.Context;

import com.operr.yelpchallenge.common.controller.backend.BaseOperation;
import com.operr.yelpchallenge.model.AuthClient;
import com.operr.yelpchallenge.model.Client;

public class GetAuthClientOperation extends BaseOperation<AuthClient> {

    private Client mClient;

    public GetAuthClientOperation(Client client, Object requestID,
                                  boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.mClient = client;
    }

    @Override
    public AuthClient doMain() throws Throwable {
        AuthClient authClient = OperationsManager.getInstance().getAuthClient(mClient);
        return authClient;
    }
}
