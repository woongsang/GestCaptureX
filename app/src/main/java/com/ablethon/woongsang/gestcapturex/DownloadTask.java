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

    private String mainTxt = "";
    private String descriptionTxt = "";
    private String weather = "";
    private String tempratureTxt = "";
    private String main = "";
    private String name = "";

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
            name = city.getString("name");
            Log.i("name", name);

            JSONArray list = jsonObject.getJSONArray("list");
            JSONObject listObject = list.getJSONObject(0);



            for (int i =0; i < 7; i++){

                JSONObject weatherInfo = listObject.getJSONArray("weather").getJSONObject(0);
                String weatherMain = weatherInfo.getString("main");
                String weatherDescription = weatherInfo.getString("description");

                JSONObject mainTemp = listObject.getJSONObject("main");
                String temp = mainTemp.getString("temp");

                Log.i ("main", weatherMain);
                Log.i ("description", weatherDescription);
                Log.i ("temp", temp);

                weather = weatherMain;
                main = weatherDescription;
                tempratureTxt = temp;

                if (!weather.equals("")){

                    mainTxt += "Day " +i+ ":  " +weather + "\r\n";
                    descriptionTxt += main + "\r\n";
                    tempratureTxt += temp + "\r\n";

                    //Toast.makeText(getBaseContext(), message + " - "
                    // + message,Toast.LENGTH_SHORT).show();

                }


            }



            //move this inside the while loop if you want to display all week



            //This displays the weather information in the Activity TextView
            if (mainTxt != ""){

                /*weatherTxt.setText(mainTxt);
                descriptionText.setText(descriptionTxt);
                temperatureText.setText(tempratureTxt);
                nameText.setText("The Weather in " +name+ " is: ");
*/


            }


        } catch (JSONException e) {


            e.printStackTrace();
        }


    }
}