package com.example.kratirastogi.garbagecollector;

import java.util.Date;

public class Data1 {
    String id,name,state;
    double lat,lng;
    com.example.kratirastogi.garbagecollector.Date date;
    String status,numplate,number;


    public Data1(String id, String name, double lat, double lng, String status, String numplate, String number, com.example.kratirastogi.garbagecollector.Date date,String state ) {

        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.date=date;
        this.status=status;
        this.numplate=numplate;
        this.number=number;
        this.state=state;


    }
    public Data1()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumplate() {
        return numplate;
    }

    public void setNumplate(String numplate) {
        this.numplate = numplate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public com.example.kratirastogi.garbagecollector.Date getDate() {
        return date;
    }

    public void setDate(com.example.kratirastogi.garbagecollector.Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
