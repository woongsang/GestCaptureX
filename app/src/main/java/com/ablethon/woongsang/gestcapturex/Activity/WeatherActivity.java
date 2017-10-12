package com.ablethon.woongsang.gestcapturex.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ablethon.woongsang.gestcapturex.API.DownloadTask;
import com.ablethon.woongsang.gestcapturex.R;

import static android.location.LocationManager.GPS_PROVIDER;


public class WeatherActivity extends Activity implements TextToSpeech.OnInitListener {

    Button WeatherInfoBtn;
    String city = "Seoul";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherInfoBtn = (Button)findViewById(R.id.WeatherInfoBtn);
        WeatherInfoBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions (new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    return;
                }
                // instantiate the location manager, note you will need to request permissions in your manifest
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // get the last know location from your location manager.
                Location location = locationManager.getLastKnownLocation(GPS_PROVIDER);
                // now get the lat/lon from the location and do something with it.
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                getWeatherAt(latitude, longitude);
            }
        });

    }

    @Override
    public void onInit(int status) {

    }

    public void getWeather(){

        Log.i ("city name", city);

        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/forecast?q="
                + city +
                "&appid=1c07e40d403816de4991116b22488b29");
    }

    public void getWeatherAt(double latitude, double longitude){

        Log.i ("latitude", Double.toString(latitude));
        Log.i ("longitude", Double.toString(longitude));

        //InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude
                        + "&lon=" + longitude +
                        "&appid=1c07e40d403816de4991116b22488b29");


    }

}
