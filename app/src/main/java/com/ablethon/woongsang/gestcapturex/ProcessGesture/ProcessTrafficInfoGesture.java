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
import com.ablethon.woongsang.gestcapturex.Activity.BusActivity;
import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.DepartureActivity;
import com.ablethon.woongsang.gestcapturex.Activity.DestinationActivity;
import com.ablethon.woongsang.gestcapturex.Activity.TrafficInfoActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-15.
 */

public class ProcessTrafficInfoGesture extends ProcessGesture {
    String name = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context, boolean isCircle) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                name = TrafficInfoActivity.getNextName(1);
                TrafficInfoActivity.myTTS.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, name + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤
                name = TrafficInfoActivity.getNextName(2);
                TrafficInfoActivity.myTTS.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, name + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (TrafficInfoActivity.nameSelector < 0) {
                    name = TrafficInfoActivity.getNextName(1);
                }

                if(name.equals("버스 도착정보")){
                    activity.startActivity(new Intent(context, BusActivity.class));
                    activity.finish();

                }else if(name.equals("길안내 도움요청")){
                    activity.startActivity(new Intent(context, DepartureActivity.class));
                    activity.finish();

                }





//                Toast.makeText(context, name +"님에게 전화를 걸겠습니다", Toast.LENGTH_LONG).show();


//                try {
//                    CallActivity.myTTS.speak(name + "님에게 전화를 걸겠습니다", TextToSpeech.QUEUE_FLUSH, null);
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }


                //  activity.finish();


            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
