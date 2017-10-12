package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-12.
 */

public class ProcessNewsGesture extends ProcessGesture {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤

            }else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤

            }else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (NewsActivity.itemSelector < 0) {
                    NewsActivity.itemSelector = 0;
                }

         }
        }
    }
}