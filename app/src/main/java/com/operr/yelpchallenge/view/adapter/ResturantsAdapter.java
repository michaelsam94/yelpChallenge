package com.operr.yelpchallenge.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.operr.yelpchallenge.common.helper.Utilities;
import com.operr.yelpchallenge.model.Resturant;

import java.util.List;

public class ResturantsAdapter extends RecyclerView.Adapter<ResturantsAdapter.ViewHolder>{

    private List<Resturant> mResturants;
    private Context mContext;

    private ResturantsAdapter(List<Resturant> resturants, Context context){
        this.mContext = context;
        this.mResturants = resturants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Utilities.isNullList(mResturants)? 0 : mResturants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
