package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class ProcessDepartureGesture extends ProcessGesture {
    String departure = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                departure = DepartureActivity.getNextName(1);
                DepartureActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤
                departure = DepartureActivity.getNextName(2);
                DepartureActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (DepartureActivity.itemSelector < 0) {
                    departure = DepartureActivity.getNextName(1);
                }

                CommonLibrary.DEPARTURE = departure;
                Log.i("출발지선택",departure+"");
                String result="출발지는 "+departure+"로 설정되었습니다.";
                DepartureActivity.myTTS.speak(result, TextToSpeech.QUEUE_FLUSH, null);

                try {
                    Thread.sleep(3000);
                    DepartureActivity.myTTS.shutdown();
                    activity.startActivity(new Intent(context, DestinationActivity.class));
                    activity.finish();



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
