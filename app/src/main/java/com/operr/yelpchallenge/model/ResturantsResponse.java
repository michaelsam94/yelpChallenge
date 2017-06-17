package com.operr.yelpchallenge.model;


import java.util.List;

public class ResturantsResponse {
    List<Business> businesses;

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }
}
