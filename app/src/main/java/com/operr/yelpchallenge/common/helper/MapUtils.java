package com.operr.yelpchallenge.common.helper;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MapUtils {


    public static void zoomToLatlng(GoogleMap map, LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f), 4000, null);
    }

}
