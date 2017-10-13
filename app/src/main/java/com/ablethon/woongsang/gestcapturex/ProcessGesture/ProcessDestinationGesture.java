package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.Activity.DepartureActivity;
import com.ablethon.woongsang.gestcapturex.Activity.DestinationActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class ProcessDestinationGesture  extends ProcessGesture {
    String departure = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                departure = DestinationActivity.getNextName(1);
                DestinationActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤
                departure = DestinationActivity.getNextName(2);
                DestinationActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (DestinationActivity.itemSelector < 0) {
                    departure = DestinationActivity.getNextName(1);
                }

                CommonLibrary.DESTINATION = departure;
                Log.i("목적지선택",departure+"");

                String result="출발지는 "+CommonLibrary.DEPARTURE+"로 도착지는 "+CommonLibrary.DESTINATION+"로 설정되었습니다." ;
                DestinationActivity.myTTS.speak(result, TextToSpeech.QUEUE_FLUSH, null);

                  activity.finish();


            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
