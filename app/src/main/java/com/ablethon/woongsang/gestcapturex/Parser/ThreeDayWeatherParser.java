package com.ablethon.woongsang.gestcapturex.Parser;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.API.DownloadTask;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ThreeDayWeatherParser extends DownloadTask {
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray obList = jsonObject.getJSONArray("list");

            double max = -100;
            double min = 100;
            double sum = 0;
            double avg;
            for (int i = 0; i < obList.length(); i++){ // every three hours starting from 6am until 9pm
                JSONObject ob = obList.getJSONObject(i);
                double temp = ob.getJSONObject("main").getDouble("temp");
                if (temp > max){
                    max = temp;
                }
                if (temp < min){
                    min = temp;
                }
                sum += temp;
            }
            avg = sum / obList.length();

            String msg = "평균 기온: " + avg + "도, 최고 기온: " + max + "도, 최저 기온: " + min;
            WeatherActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);

            Log.i ("avg", Double.toString(avg));
            Log.i ("max", Double.toString(max));
            Log.i ("min", Double.toString(min));

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
