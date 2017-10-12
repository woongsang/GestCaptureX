package com.ablethon.woongsang.gestcapturex;

/**
 * Created by SangHeon on 2017-10-10.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class CallActivity extends Activity implements OnInitListener{

    public static TextToSpeech myTTS;

    public static ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    static int nameSelector = -1;
    Context context=this;
    int callChecker = 0;
    int scrollMovingDirection;
    private float mInitialX;
    private float mInitialY;
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

        listview= (ListView) findViewById(R.id.ListView);
        listview.setAdapter(adapter);

        listview.setOnTouchListener(scrollChecker);
     //   listview.setOnScrollListener(scrollListener);
    }



    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)

        ProcessCallGesture pg= new ProcessCallGesture();                               //to prcessing gesture
        TouchInterface TI = new TouchInterface((Activity) context,context,pg);       //to prcessing gesture

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
        String myText1 = "안녕하세요구르트";
        String myText2 = "반갑다람쥐.";
       myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
       myTTS.speak(myText2, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.shutdown();
    }
}
