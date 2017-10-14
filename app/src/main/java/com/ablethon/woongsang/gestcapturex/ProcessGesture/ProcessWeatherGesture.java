package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProcessWeatherGesture extends ProcessGesture {
    String selected_option = "현재 날씨";

    @RequiresApi(api = Build.VERSION_CODES.M)

    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context, boolean isCircle) throws IOException {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions (new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }

        double latitude = 37.4, longitude = 127.1;

        Geocoder gCoder = new Geocoder(context, Locale.getDefault());
        Log.i(Double.toString(latitude), Double.toString(longitude));
        List<Address> addr = gCoder.getFromLocation(latitude, longitude, 1);
        Address a = addr.get(0);
        String city_name = a.getLocality();

        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                selected_option = WeatherActivity.getNextOption(1);
                WeatherActivity.myTTS.speak(city_name + " " + selected_option, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, city_name + " " + selected_option, Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //아래->위로 스크롤
                selected_option = WeatherActivity.getNextOption(2);
                WeatherActivity.myTTS.speak(city_name + " " + selected_option, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, city_name + " " + selected_option, Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (WeatherActivity.selector < 0) {

                    WeatherActivity.selector = 0;

                }



                WeatherActivity.getWeather(latitude, longitude, selected_option);

            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
