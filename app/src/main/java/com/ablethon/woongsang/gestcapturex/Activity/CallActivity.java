package com.ablethon.woongsang.gestcapturex.Activity;

/**
 * Created by SangHeon on 2017-10-10.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;

import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CallActivity extends Activity implements OnInitListener{

    public static TextToSpeech myTTS;

    public static ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    public static int nameSelector = -1;
    Context context=this;
    int callChecker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        CommonLibrary.initPersonList();
        nameSelector = -1;
        myTTS = new TextToSpeech(this, this);
        callChecker=0;

        for(int i=0;i<CommonLibrary.PERSON_LIST.size();i++){
            mDatas.add( CommonLibrary.PERSON_LIST.get(i).getName() );
        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView) findViewById(R.id.CallListView);
        listview.setAdapter(adapter);

//        listview.setOnTouchListener(scrollChecker);

    }

    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessCallGesture pg= new ProcessCallGesture();                               //to prcessing gesture
        TouchInterface TI = new TouchInterface((Activity) context,context,pg);       //to prcessing gesture

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.CALL_PHONE}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return false;
            }

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
    public static String getNextName(int operator){
        if(operator==1){
            if(nameSelector < mDatas.size()-1 ) {
                nameSelector++;
            }else {
                nameSelector = 0;
            }
        }else{
            if(nameSelector > 0 ) {
                nameSelector--;
            }else {
                if(nameSelector==-1){
                    nameSelector=0;
                }else {
                    nameSelector = mDatas.size() - 1;
                }
            }
        }
        return mDatas.get(nameSelector);
    }

    public void onInit(int status) {
        String myText1 = "위 아래로 드래그하여 전화할 사람을 선택해주세요";
        String myText2 = "선택 후 전화하려면 좌에서 우로 드래그 해주세요";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        myTTS.speak(myText2, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void onBackPressed() {
        if(myTTS != null) {

            myTTS.stop();
            myTTS.shutdown();
            Log.d(TAG, "TTS Destroyed");
        }
        finish();


    }
}