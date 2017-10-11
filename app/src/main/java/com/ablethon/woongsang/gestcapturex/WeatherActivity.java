package com.ablethon.woongsang.gestcapturex;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class WeatherActivity extends Activity implements TextToSpeech.OnInitListener {

    //private TextToSpeech myTTS;
    Button cityName;
    //Button weatherText;
    //Button nameText;

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = (Button)findViewById(R.id.cityName);
        cityName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // instantiate the location manager, note you will need to request permissions in your manifest
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // get the last know location from your location manager.
                //Location location = locationManager.getLastKnownLocation(GPS_PROVIDER);
                // now get the lat/lon from the location and do something with it.
                //double latitude = location.getLatitude();
                //double longitude = location.getLongitude();
                //Log.i (latitude + " " + longitude, "tttttttttttttttttttttttt");
                city = "seoul";
                Log.i ("city name", city);
                getWeather();
            }
        });
        //weatherText = (Button)findViewById(R.id.weatherText);
        //nameText = (Button)findViewById(R.id.nameText);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //cityName = (EditText) findViewById(R.id.cityName);
        //weatherText = (TextView) findViewById(R.id.weatherText);
        //nameText = (TextView) findViewById(R.id.cityNameText);

    }

    @Override
    public void onInit(int status) {

    }

    public void getWeather(){

        Log.i ("city name", city);

        //InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        DownloadTask task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/forecast?q="
                + city +
                "&appid=71aefce4499fe64969286582c7e80ea2");


    }

}
