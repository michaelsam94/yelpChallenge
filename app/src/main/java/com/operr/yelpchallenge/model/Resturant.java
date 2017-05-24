package com.operr.yelpchallenge.model;


import java.util.List;

public class Resturant {
    private List<Business> businesses;
    private int total;


    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
