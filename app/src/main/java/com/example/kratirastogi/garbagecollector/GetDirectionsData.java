package com.example.kratirastogi.garbagecollector;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.HashMap;


/**
 * @auth Priyanka
 */

public class GetDirectionsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng,l;

    LocationManager lm;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];
        l=(LatLng) objects[3];



        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        String[] directionsList;
        HashMap<String,String> d=null;
        DataParser parser = new DataParser();
        d=parser.parseDirections1(s);
        directionsList = parser.parseDirections(s);
       duration=  d.get("duration");

      String distance=d.get("distance");
      mMap.clear();

        mMap.addMarker(new MarkerOptions().position(l).title("Current").icon(BitmapDescriptorFactory.defaultMarker()));
      MarkerOptions markerOptions=new MarkerOptions();
      markerOptions.position(latLng);
      markerOptions.title("Duration ="+duration);
      markerOptions.snippet("Distance ="+distance);
      mMap.addMarker(markerOptions);
       displayDirection(directionsList);





    }

    public void displayDirection(String[] directionsList)
    {

        int count = directionsList.length;
        for(int i = 0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);
        }
    }


 public String data()
 {
     return duration;
 }



}
