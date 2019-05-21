package com.example.kratirastogi.garbagecollector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    String add;
    LatLng l;
    LocationManager lm;
    MarkerOptions m;
    double latitude, longitude;
    LatLng a;
    Object dataTransfer[];
    GetDirectionsData getDirectionsData;
    GPSTracker gps;
    LocationManager locationManager;
    SupportMapFragment mapFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent i = getIntent();
        gps = new GPSTracker(MapsActivity.this);
        add = i.getStringExtra("add");






        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (gps.canGetLocation()) {
             latitude = gps.getLatitude();
             longitude = gps.getLongitude();
            l = getLocationFromAddress(this, add);
            LatLng sydney = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Current").icon(BitmapDescriptorFactory.fromResource(R.mipmap.address)));

            LatLng sd = new LatLng(l.latitude, l.longitude);
            mMap.addMarker(new MarkerOptions().position(sd).title("Parking").icon(BitmapDescriptorFactory.fromResource(R.mipmap.dustbin)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));


            dataTransfer = new Object[4];
            String url = getDirectionsUrl();
            getDirectionsData = new GetDirectionsData();
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            dataTransfer[2] = new LatLng(l.latitude, l.longitude);
            dataTransfer[3] = new LatLng(latitude, longitude);
            getDirectionsData.execute(dataTransfer);
        }
            else
        {
            gps.showSettingsAlert();
            mapFragment.getMapAsync(this);
        }







    }

    private String getDirectionsUrl() {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" +latitude+ "," + longitude);
        googleDirectionsUrl.append("&destination=" + l.latitude + "," + l.longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyBuMohJ0c9TIzHEmlw99vfNa8DJYei0A6I");

        return googleDirectionsUrl.toString();
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }












}
