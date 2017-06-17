package com.operr.yelpchallenge.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.operr.yelpchallenge.R;
import com.operr.yelpchallenge.common.helper.Utilities;
import com.operr.yelpchallenge.model.Business;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResturantsAdapter extends RecyclerView.Adapter<ResturantsAdapter.ViewHolder>{

    private List<Business> mBussineses;
    private Context mContext;
    private LayoutInflater mInflater;

    public ResturantsAdapter(List<Business> businesses, Context context){
        this.mContext = context;
        this.mBussineses = businesses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mInflater == null) mInflater = LayoutInflater.from(mContext);
        return new ViewHolder(mInflater.inflate(R.layout.item_resturant, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Business business = mBussineses.get(position);

        if(business.getName() != null) holder.tvResturantTitle.setText(business.getName());
        if(business.getLocation().getLocationFormatted() != null) holder.tvResturantLocation.setText(business.getLocation().getLocationFormatted());
        if(business.getRating() != 0) holder.rbResturantRating.setRating(business.getRating());
        if(business.getDistance() != 0.0d) holder.tvDistance.setText(business.getDistance() + "");

    }

    @Override
    public int getItemCount() {
        return Utilities.isNullList(mBussineses)? 0 : mBussineses.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_resturant)
        ImageView ivResturant;
        @BindView(R.id.tv_resturant_tile)
        TextView tvResturantTitle;
        @BindView(R.id.tv_resturant_location)
        TextView tvResturantLocation;
        @BindView(R.id.rb_resturant_rating)
        RatingBar rbResturantRating;
        @BindView(R.id.tv_distance)
        TextView tvDistance;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
