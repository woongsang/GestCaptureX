package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.BusActivity;
import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.MainActivity;
import com.ablethon.woongsang.gestcapturex.Activity.RoadAnounceActivity;
import com.ablethon.woongsang.gestcapturex.Activity.TrafficInfoActivity;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

/**
 * Created by woong on 2017-10-15.
 */

public class ProcessMainGesture extends ProcessGesture {
    private static Intent intent = new Intent();
    private static boolean isSelected = false;

    public void process(ArrayList<Vertex> list, Activity activity, Context context, boolean isCircle){


        if(list.size() == 1 && list.get(0).getSection() % 2 == 0){

            String msg = "";
            isSelected = true;

            if(list.get(0).getSection() == 0){
                msg = "날씨";
                Log.i("tttt", msg);
                MainActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(context, WeatherActivity.class);
            }
            else if (list.get(0).getSection() == 2){
                msg = "전화";
                Log.i("tttt", msg);
                MainActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(context, CallActivity.class);
            }
            else if (list.get(0).getSection() == 4){
                msg = "길안내";
                Log.i("tttt", msg);
                MainActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(context, RoadAnounceActivity.class);
            }else if (list.get(0).getSection() == 6){
                msg = "교통정보";
                Log.i("tttt", msg);
                MainActivity.myTTS.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(context, TrafficInfoActivity.class);
            }
        }
        Log.i("is circle? ", Boolean.toString(isCircle));
        Log.i("is selected? ", Boolean.toString(isSelected));
        if (isSelected && isCircle) {
            activity.startActivity(intent);
        }
    }
}
