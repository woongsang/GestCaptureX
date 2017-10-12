package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;
import com.ablethon.woongsang.gestcapturex.Activity.WeatherActivity;

import java.util.ArrayList;



/**
 * Created by SangHeon on 2017-10-10.
 */

public class ProcessGesture  extends Activity{

    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context){}

    public void processMainGesture(ArrayList<Vertex> list, Activity activity, Context context){

        if(list.size() == 2){
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
