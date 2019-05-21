package com.example.kratirastogi.garbagecollector;

public class Info {
    String id;
    Data c;
    Info()
    {

    }
    public Info(String id, Data c) {
        this.id = id;
        this.c = c;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data getC() {
        return c;
    }

    public void setC(Data c) {
        this.c = c;
    }



}
