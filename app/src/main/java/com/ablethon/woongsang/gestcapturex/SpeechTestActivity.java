package com.ablethon.woongsang.gestcapturex;

/**
 * Created by SangHeon on 2017-10-10.
 */

import android.Manifest;
import android.app.Activity;
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

public class SpeechTestActivity extends Activity implements OnInitListener {

    private TextToSpeech myTTS;

    ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    int nameSelector = -1;

    int callChecker = 0;
    int scrollMovingDirection;
    private float mInitialX;
    private float mInitialY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_test);

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
        listview.setOnScrollListener(scrollListener);
    }

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener(){
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {    }
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            if(scrollState == 0){
                if(scrollMovingDirection==1){
                    Log.i("스크롤이동:", "아래");
                    String name = mDatas.get(getNextIndex(1));
                    Toast.makeText(getApplicationContext(), name + " ", Toast.LENGTH_SHORT).show();
                    myTTS.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }else if(scrollMovingDirection==2){
                    Log.i("스크롤 이동:", "위");
                    String name = mDatas.get(getNextIndex(2));
                    Toast.makeText(getApplicationContext(), name + " ", Toast.LENGTH_SHORT).show();
                    myTTS.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    };

    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (new String[]{Manifest.permission.CALL_PHONE}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return false;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mInitialX = event.getX();
                    mInitialY = event.getY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    final float x = event.getX();
                    final float y = event.getY();
                    final float yDiff = y - mInitialY;
                    final float xDiff = x - mInitialX;
                    if(Math.abs(xDiff)>50){
                        Log.i("스와이프", "스와이프");

                        if(nameSelector<0){
                            nameSelector=0;
                        }
                        callChecker++;

                       //한번만 호출하기 위해 callChecker을 설정함.(없으면 스와이프가 지속적으로 인식되어 여러번 호출됨)
                        if(callChecker == 1){

                            String name = mDatas.get(nameSelector);
                            myTTS.speak(name+"님에게 전화를 걸겠습니다", TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String phone = "tel:"+CommonLibrary.getPhoneNumber(name);
                            startActivity(new Intent("android.intent.action.CALL", Uri.parse(phone)));

                            finish();
                        }
                        return true;
                    }
                    if (yDiff > 0.0 && Math.abs(yDiff)>50) {
                        scrollMovingDirection=1;
                     //   Log.i("스크롤이동이동", "아래");
                        break;
                    } else if (yDiff < 0.0 && Math.abs(yDiff)>50) {
                        scrollMovingDirection=2;
                    //    Log.i("스크롤이동이동", "위");
                        break;
                    }
                    break;
            }
            return false;
        }
    };


    /*
    *  다음 인덱스를 구하는 메소드
    *  operator이 2이면 위로이동 1이면 아래로 이동
    * */
    public int getNextIndex(int operator){
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
        return nameSelector;
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
