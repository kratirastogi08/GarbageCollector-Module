package com.example.kratirastogi.garbagecollector;

public class Date3 {
    String date;
    double lat,lng;

    public Date3(String date, double lat, double lng) {
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }
    Date3()
    {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
