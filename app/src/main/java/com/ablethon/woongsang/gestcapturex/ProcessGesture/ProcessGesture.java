package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.MainActivity;
import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by SangHeon on 2017-10-10.
 */

public class ProcessGesture  extends Activity{

    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context){}

    public void processMainGesture(ArrayList<Vertex> list, Activity activity, Context context){



        if(list.size() == 1){
            if(list.get(0).getSection() == 0 ){
                Log.i("tttt","좌->우입니다....");
                // 현재시간을 msec 으로 구한다.
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
                SimpleDateFormat sdfNow = new SimpleDateFormat("현재시각 HH시 mm분 입니다.");
                // nowDate 변수에 값을 저장한다.
                String formatDate = sdfNow.format(date);
                MainActivity.myTTS.speak(formatDate, TextToSpeech.QUEUE_FLUSH, null);


            }


        }
        else if(list.size() == 2){
            if(list.get(0).getSection() == 0 && list.get(1).getSection() == 2){
                Log.i("tttt","ㄱ입니다....");
                activity.startActivity(new Intent(context, CallActivity.class));
            }
            else if (list.get(0).getSection() == 2 && list.get(1).getSection() == 0){
                Log.i("tttt", "ㄴ입니다....");
                activity.startActivity(new Intent(context, WeatherActivity.class));
            }
            else if (list.get(0).getSection() == 4 && list.get(1).getSection() == 2 ){
                Log.i("tttt","ㄱ반대입니다....");
                activity.startActivity(new Intent(context, NewsActivity.class));
            }
        }
    }

}
