package com.ablethon.woongsang.gestcapturex.Parser;

import android.location.Address;
import android.location.Geocoder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.API.DownloadTask;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentWeatherParser extends DownloadTask {
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            String temp;
            String weatherDesc;

            JSONObject jsonObject = new JSONObject(result);
            String cityName = jsonObject.getString("name");
            Log.i(cityName, "city.getString(name)---------------------");


            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            //description is output in Korean
            weatherDesc = weather.getString("description");

            JSONObject main = jsonObject.getJSONObject("main");
            //temperature in celcius
            temp = main.getString("temp");

            String date = jsonObject.getString("dt");

            String msg = "현재 기온: " + temp + "도, 상태: " + weatherDesc;
            WeatherActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
            Log.i ("description", weatherDesc);
            Log.i ("temp", temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
