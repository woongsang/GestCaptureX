package com.ablethon.woongsang.gestcapturex;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);

            urlConnection =  (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1){

                char current = (char) data;

                result += current;

                data = reader.read();
            }

            return result;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

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