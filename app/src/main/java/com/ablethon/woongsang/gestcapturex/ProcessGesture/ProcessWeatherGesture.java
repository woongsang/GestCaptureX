package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

import static android.location.LocationManager.GPS_PROVIDER;


public class ProcessWeatherGesture extends ProcessGesture {
    String selected_option = "현재 날씨";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                selected_option = WeatherActivity.getNextOption(1);
                WeatherActivity.myTTS.speak(selected_option, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, selected_option + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //아래->위로 스크롤
                selected_option = WeatherActivity.getNextOption(2);
                WeatherActivity.myTTS.speak(selected_option, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, selected_option + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (WeatherActivity.selector < 0) {

                    WeatherActivity.selector = 0;

                }

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions (new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    return;
                }
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(GPS_PROVIDER);
                WeatherActivity.getWeather(location);

//                Toast.makeText(context, name +"님에게 전화를 걸겠습니다", Toast.LENGTH_LONG).show();


//                try {
//                    CallActivity.myTTS.speak(name + "님에게 전화를 걸겠습니다", TextToSpeech.QUEUE_FLUSH, null);
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


              //  activity.finish();


            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
