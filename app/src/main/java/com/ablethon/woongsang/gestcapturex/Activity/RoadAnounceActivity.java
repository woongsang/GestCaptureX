package com.ablethon.woongsang.gestcapturex.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.API.References;
import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessRoadAnounceGesture;
import com.ablethon.woongsang.gestcapturex.R;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;
import java.util.Collection;

import static android.content.ContentValues.TAG;

/**
 * Created by SangHeon on 2017-10-14.
 */

public class RoadAnounceActivity extends Activity implements TextToSpeech.OnInitListener {

    public static TextToSpeech myTTS;
    public static ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    public static int itemSelector = -1;
    Context context=this;
    int callChecker = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anounce_road);
        myTTS = new TextToSpeech(this, this);
      //  readBeacon();
      //  CommonLibrary.initPersonList();
        itemSelector = -1;

      //  callChecker=0;

//        for(int i=0;i<CommonLibrary.PERSON_LIST.size();i++){
//            mDatas.add( CommonLibrary.PERSON_LIST.get(i).getName() );
//        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView) findViewById(R.id.RoadAnounceListView);
        listview.setAdapter(adapter);







     //   listview.setOnTouchListener(scrollChecker);

    }

    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessRoadAnounceGesture pg= new ProcessRoadAnounceGesture();                               //to prcessing gesture
        TouchInterface TI = new TouchInterface((Activity) context,context,pg);       //to prcessing gesture

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if(TI.gestureInterface(event)){    //to prcessing gesture
                return true;                //to prcessing gesture
            }else{                          //to prcessing gesture
                return false;            //to prcessing gesture
            }
        }
    };


    /*
    *  다음 인덱스를 구하는 메소드
    *  operator이 2이면 위로이동 1이면 아래로 이동
    * */
    public static String getNextItem(int operator){
        if(operator==1){
            if(itemSelector < mDatas.size()-1 ) {
                itemSelector++;
            }else {
                itemSelector = 0;
            }
        }else{
            if(itemSelector > 0 ) {
                itemSelector--;
            }else {
                if(itemSelector ==-1){
                    itemSelector =0;
                }else {
                    itemSelector = mDatas.size() - 1;
                }
            }
        }
        return mDatas.get(itemSelector);
    }

    public void onInit(int status) {

        String myText1 = "길 안내를 시작합니다,";

        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myTTS.speak("현재위치는 화장실 앞 입니다. 엘레베이터 20m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myTTS.speak("현재위치는 화장실 앞 입니다. 엘레베이터 12m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myTTS.speak("현재위치는 엘레베이터 앞 입니다. 강당 20m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void onBackPressed() {


        Thread.interrupted();
        String myText1 = "비콘을 이용한 길안내를 종료합니다.";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);

        if(myTTS != null) {

            myTTS.stop();
            myTTS.shutdown();
            Log.d(TAG, "TTS Destroyed");
        }
        finish();

    }


    }
