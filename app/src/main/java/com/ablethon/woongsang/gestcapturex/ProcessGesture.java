package com.ablethon.woongsang.gestcapturex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;



/**
 * Created by SangHeon on 2017-10-10.
 */

public class ProcessGesture  extends Activity{
    public void processGesture(ArrayList<Vertex> list,Activity activity,Context context){

        if(list.size() == 2){
            if(list.get(0).getSection() == 0 && list.get(1).getSection() == 2){
                Log.i("tttt","ㄱ입니다....");
                activity.startActivity(new Intent(context, CallActivity.class));
            }
            else if (list.get(0).getSection() == 2 && list.get(1).getSection() == 0){
                Log.i("tttt", "ㄴ입니다....");
                activity.startActivity(new Intent(context, WeatherActivity.class));
            }
        }
    }
}
