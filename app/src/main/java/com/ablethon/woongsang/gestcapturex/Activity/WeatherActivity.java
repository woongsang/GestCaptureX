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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.DownloadTask;
import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.Parser.WeatherFiveDays;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessWeatherGesture;
import com.ablethon.woongsang.gestcapturex.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.location.LocationManager.GPS_PROVIDER;


public class WeatherActivity extends Activity implements TextToSpeech.OnInitListener {

    public static TextToSpeech myTTS;

    public static ArrayList<String> options = new ArrayList<String>();
    ListView listview;
    public static int selector;
    Context context = this;

    Button WeatherInfoBtn;

    static DownloadTask task = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        options.clear();
        options.add("현재 날씨");
        options.add("오늘의 날씨");
        options.add("일주일 날씨");
        selector = -1;
        myTTS = new TextToSpeech(this, this);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.bigfont_item, options);
        listview = (ListView) findViewById(R.id.WeatherListView);
        listview.setAdapter(adapter);

        listview.setOnTouchListener(scrollChecker);
    }


    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessWeatherGesture pg= new ProcessWeatherGesture();                               //to prcessing gesture
        TouchInterface TI = new TouchInterface((Activity) context,context,pg);       //to prcessing gesture

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return false;
            }
            return TI.gestureInterface(event);
        }
    };

    public static String getNextOption(int operator){
        if(operator==1){
            if(selector < options.size()-1 ) {
                selector++;
            }else {
                selector = 0;
            }
        }else{
            if(selector > 0 ) {
                selector--;
            }else {
                if(selector==-1){
                    selector=0;
                }else {
                    selector = options.size() - 1;
                }
            }
        }
        return options.get(selector);
    }

    @Override
    public void onInit(int status) {

    }

    public static void getWeather(Location location){

        task = new WeatherFiveDays();

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        task.execute("http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude
                + "&lon=" + longitude +
                "&appid=1c07e40d403816de4991116b22488b29");
    }

    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if(myTTS != null) {

            myTTS.stop();
            myTTS.shutdown();
            Log.d(TAG, "TTS Destroyed");
        }
        super.onDestroy();
    }
}
