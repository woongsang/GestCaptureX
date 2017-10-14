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

            double max1 = -100, max2 = -100, max3 = -100;
            double min1 = 100, min2 = 100, min3 = 100;
            double sum1 = 0, sum2 = 0, sum3 = 0;
            double avg1 = 0, avg2 = 0, avg3 = 0;

            for (int i = 0; i < obList.length(); i++){
                if ( i / 8 == 0) { // day 1
                    JSONObject ob = obList.getJSONObject(i);
                    double temp = ob.getJSONObject("main").getDouble("temp");
                    if (temp > max1) {
                        max1 = temp;
                    }
                    if (temp < min1) {
                        min1 = temp;
                    }
                    sum1 += temp;
                }
                else if (i / 8 == 1) { // day 2
                    JSONObject ob = obList.getJSONObject(i);
                    double temp = ob.getJSONObject("main").getDouble("temp");
                    if (temp > max2) {
                        max2 = temp;
                    }
                    if (temp < min2) {
                        min2 = temp;
                    }
                    sum2 += temp;
                }
                else if (i / 8 == 2) { // day 3
                    JSONObject ob = obList.getJSONObject(i);
                    double temp = ob.getJSONObject("main").getDouble("temp");
                    if (temp > max3) {
                        max3 = temp;
                    }
                    if (temp < min3) {
                        min3 = temp;
                    }
                    sum3 += temp;
                }

                avg1 = sum1 / 8;
                avg2 = sum2 / 8;
                avg3 = sum3 / 8;
            }


            String msg1 = "오늘 평균 기온: " + (int)avg1 + "도, 최고 기온: " + (int)max1 + "도, 최저 기온: " + (int)min1 + "도 입니다." +
                        " 내일 평균 기온: " + (int)avg2 + "도, 최고 기온: " + (int)max2 + "도, 최저 기온: " + (int)min2 + "도 입니다" +
                        " 모레 평균 기온: " + (int)avg3 + "도, 최고 기온: " + (int)max3 + "도, 최저 기온: " + (int)min3 + "도 입니다.";

            WeatherActivity.myTTS.speak(msg1, TextToSpeech.QUEUE_FLUSH, null);

            Log.i ("avg", Double.toString(avg1));
            Log.i ("max", Double.toString(max1));
            Log.i ("min", Double.toString(min1));

            Log.i ("avg", Double.toString(avg2));
            Log.i ("max", Double.toString(max2));
            Log.i ("min", Double.toString(min2));

            Log.i ("avg", Double.toString(avg3));
            Log.i ("max", Double.toString(max3));
            Log.i ("min", Double.toString(min3));
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
