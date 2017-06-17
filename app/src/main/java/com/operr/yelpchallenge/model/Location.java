package com.operr.yelpchallenge.model;


public class Location {
    private String address1;
    private String city;
    private String country;
    private String state;
    private double distance;


    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLocationFormatted(){
        StringBuilder builder = new StringBuilder();
        if(this.address1 != null) builder.append(address1 + "\n");
        if(this.city != null) builder.append(city + "\n");
        if(this.state != null) builder.append(state + " ");
        if(this.country != null) builder.append(country);
        return builder.toString();
    }

}
