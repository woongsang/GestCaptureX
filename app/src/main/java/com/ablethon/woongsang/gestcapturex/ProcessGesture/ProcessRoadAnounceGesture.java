package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-14.
 */

public class ProcessRoadAnounceGesture extends ProcessGesture {
    String title= "";
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context, boolean isCircle) {



        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                Log.i("누름","위아래로");
            //    title = RoadAnounceActivity.getNextItem(1);
             //   NewsActivity.myTTS.speak(title, TextToSpeech.QUEUE_FLUSH, null);
             //   Toast.makeText(context, title + " ", Toast.LENGTH_LONG).show();
            }else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤
                Log.i("누름","아래위로");
            //    title = RoadAnounceActivity.getNextItem(2);
             //   NewsActivity.myTTS.speak(title, TextToSpeech.QUEUE_FLUSH, null);
            //    Toast.makeText(context, title + " ", Toast.LENGTH_LONG).show();
            }else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (NewsActivity.itemSelector < 0) {
                 //   title = RoadAnounceActivity.getNextItem(1);
                }
             //   String description = CommonLibrary.getArticleDescription(title);
             //   NewsActivity.myTTS.speak(description, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }
}