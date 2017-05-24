package com.operr.yelpchallenge.common.controller.backend;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.view.ProgressDialogView;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class BaseOperation<T> extends AsyncTask<Void, Object, CTOperationResponse> {


    private static HashMap<String, BaseOperation<?>> activeOperations = new HashMap<String, BaseOperation<?>>();
    private static HashMap<Object, BaseOperation<?>> activeOperationsMapByRequstId = new HashMap<Object, BaseOperation<?>>();
    protected boolean isShowLoadingDialog = true;
    protected Context context;
    protected ProgressDialogView dialog;
    protected Object requestID = 0;
    protected long operationUniqueID = 0;
    public boolean isOperationRuning = false;
    ArrayList<RequestObserver> observersList;

    public BaseOperation() {
        operationUniqueID = System.currentTimeMillis();

    }

    public BaseOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        this.isShowLoadingDialog = isShowLoadingDialog;
        this.context = activity;
        this.requestID = requestID;

        observersList = new ArrayList<RequestObserver>();
    }

    public static BaseOperation<?> getActiveOperation(Class<? extends BaseOperation<?>> operationClass) {
        return activeOperations.get(operationClass.getName());
    }

    public static BaseOperation<?> getActiveOperationByRequestId(Object requestId) {

        if (requestId != null)
            return activeOperationsMapByRequstId.get(requestId);
        return null;
    }

    /*
     * Check if the JSON response is succeeded
     */
    protected static void ensureRequestSucceeded(JSONObject responseJSON) {
//		if (responseJSON != null)
//		{
//			String statusCode = responseJSON.optString(ServerKeys.STATUS_CODE);
//			String statusMessage = responseJSON.optString(ServerKeys.STATUS_MESSAGE);
//
//			if (!Utilities.isNullString(statusCode))
//			{
//				double status = Double.valueOf(statusCode);
//				if (status != HttpURLConnection.HTTP_OK && status != HttpURLConnection.HTTP_CREATED
//				  && status != HttpURLConnection.HTTP_ACCEPTED)
//					throw new CTHttpError(statusMessage, Double.valueOf(statusCode));
//			}
//		}
    }

    /**
     * Do/Execute the operation itself
     *
     * @return the object
     * @throws Exception
     */
    public abstract T doMain() throws Throwable;

    protected void showWaitingDialog() {
        if (dialog == null)
            dialog = new ProgressDialogView(context);

        dialog.setCanceledOnTouchOutside(false);
//        if (!dialog.isShowing())
//            dialog.showProgressDialog(context.getString(R.string.wait_moment));

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.v("Dialog", "Canceled ");
                cancelConn();

                BaseOperation.this.cancel(true);

            }
        });

        try {
            if (!dialog.isShowing())
                dialog.showProgressDialog(context.getString(R.string.wait_moment));
        } catch (Exception e) {
        } // Show dialog on activity killed
    }

    private void cancelConn() {
    }

    @Override
    protected void onPreExecute() {
        isOperationRuning = true;
        activeOperations.put(this.getClass().getName(), this);
        if (requestID != null)
            activeOperationsMapByRequstId.put(requestID, this);
        super.onPreExecute();

        if (isShowLoadingDialog) {
            showWaitingDialog();
        }

    }

    @Override
    protected CTOperationResponse doInBackground(Void... params) {
        CTOperationResponse response = new CTOperationResponse();
        try {
            response.response = doMain();
        } catch (SocketTimeoutException t) {
            response.error = new CTHttpError("Request timmed out", 504);

        } catch (Throwable t) {
            if (!(t instanceof CTHttpError)) t.printStackTrace();
            response.error = t;
        }

        return response;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (isShowLoadingDialog && dialog.isShowing()) dialog.hideDialog();
    }

    @Override
    protected void onPostExecute(CTOperationResponse result) {
        isOperationRuning = false;
        activeOperations.remove(this.getClass().getName());
        if (requestID != null)
            activeOperationsMapByRequstId.remove(requestID);

        super.onPostExecute(result);
        try {
            if (isShowLoadingDialog && dialog != null && dialog.isShowing())
                dialog.hideDialog();
        } catch (Exception ex) {
            //ignore exception, as this happens sometimes
            ex.printStackTrace();
        }

        doOnPostExecute(result);
        // Wake observers with the result
        for (RequestObserver observer : observersList) {

            observer.handleRequestFinished(requestID, result.error, result.response);
        }
    }

    protected void doOnPostExecute(CTOperationResponse result) {
    }


    /*
     * ******************************************************************
     * ********************** Observers Handling ************************
     * ******************************************************************
     */
    /*
     * Add Request Observer to List
	 */
    public BaseOperation<T> addRequsetObserver(RequestObserver requestObserver) {
        // remove the observer if it was already added here
        removeRequestObserver(requestObserver);
        // add to observers List
        observersList.add(requestObserver);

        return this;
    }

    /*
     * Remove Request Observer from the list
     */
    public void removeRequestObserver(RequestObserver requestObserver) {
        observersList.remove(requestObserver);
    }


    public boolean isShowLoadingDialog() {
        return isShowLoadingDialog;
    }

    /*
     * Setters & Getters
     */
    public void setShowLoadingDialog(boolean isShowLoadingDialog) {
        this.isShowLoadingDialog = isShowLoadingDialog;
    }
}
