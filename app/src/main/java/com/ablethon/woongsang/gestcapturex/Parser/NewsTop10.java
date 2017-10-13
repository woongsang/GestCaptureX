package com.ablethon.woongsang.gestcapturex.Parser;

import android.util.Log;

import com.ablethon.woongsang.gestcapturex.API.DownloadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class NewsTop10 extends DownloadTask {
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            JSONObject jsonObject = new JSONObject(result);

            JSONObject city = jsonObject.getJSONObject("city");
            String cityName = city.getString("name");
            Log.i(cityName, "city.getString(name)---------------------");

            JSONArray list = jsonObject.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject listObject = list.getJSONObject(i);

                JSONObject weather = listObject.getJSONArray("weather").getJSONObject(0);
                String weatherMain = weather.getString("main");
                String weatherDesc = weather.getString("description");

                JSONObject tempInfo = listObject.getJSONObject("main");
                String temp = tempInfo.getString("temp");

                String date = listObject.getString("dt_txt");

                Log.i ("main", weatherMain);
                Log.i ("description", weatherDesc);
                Log.i ("temp", temp);
                Log.i ("date", date);
            }

        } catch (JSONException e) {


            e.printStackTrace();
        }
    }
}
