package com.ablethon.woongsang.gestcapturex;

/**
 * Created by SangHeon on 2017-10-10.
 */

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
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

        for(int i=0;i<CommonLibrary.PERSON_LIST.size();i++){
            mDatas.add( CommonLibrary.PERSON_LIST.get(i).getName() );
        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView) findViewById(R.id.ListView);
        listview.setAdapter(adapter);

        listview.setOnLongClickListener(longClickListener);
        listview.setOnTouchListener(scrollChecker);
        listview.setOnScrollListener(scrollListener);


    }

View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        if(nameSelector<0){
            nameSelector=0;
        }
        myTTS.speak(mDatas.get(getNextIndex(nameSelector))+"에게 전화를 거시겠습니까?", TextToSpeech.QUEUE_FLUSH, null);


        return true;
    }
};

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
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mInitialX = event.getX();
                    mInitialY = event.getY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    final float x = event.getX();
                    final float y = event.getY();
                    final float yDiff = y - mInitialY;
                    if (yDiff > 0.0) {
                        scrollMovingDirection=1;
                     //   Log.i("스크롤이동이동", "아래");
                        break;

                    } else if (yDiff < 0.0) {
                        scrollMovingDirection=2;
                    //    Log.i("스크롤이동이동", "위");
                        break;
                    }
                    break;
            }
            return false;
        }
    };


    AdapterView.OnTouchListener lis = new  AdapterView.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
              //      String name = mDatas.get(getNextIndex(NameSelector));
               //     Toast.makeText(getApplicationContext(), name + "ACTION_DOWN ", Toast.LENGTH_SHORT).show();
                    Log.i("ACTION_DOWNddd", "ACTION_DOWNddd");

                case MotionEvent.ACTION_SCROLL:
                    //    Toast.makeText(getApplicationContext(), "ACTION_SCROLL ", Toast.LENGTH_SHORT).show();
                    Log.i("ACTION_SCROLLfff", "ACTION_SCROLLfff");


                case MotionEvent.ACTION_HOVER_MOVE:
               //     Toast.makeText(getApplicationContext(), "ACTION_HOVER_MOVE ", Toast.LENGTH_SHORT).show();
                    Log.i("ACTION_HOVER_MOVE","ACTION_HOVER_MOVE");

                case MotionEvent.ACTION_MOVE:
                //    Toast.makeText(getApplicationContext(), "ACTION_MOVE ", Toast.LENGTH_SHORT).show();
                    Log.i("ACTION_MOVE","ACTION_MOVE");

                case MotionEvent.ACTION_POINTER_DOWN:
            //        Toast.makeText(getApplicationContext(), "ACTION_POINTER_DOWN ", Toast.LENGTH_SHORT).show();
                    Log.i("ACTION_POINTER_DOWN","ACTION_POINTER_DOWN");

            }


          //  myTTS.speak("짧게누름"+name, TextToSpeech.QUEUE_FLUSH, null);
            return true;
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




    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = mDatas.get(position);
            String phone =  CommonLibrary.getPhoneNumber(name);
            myTTS.speak("짧게누름"+name+phone, TextToSpeech.QUEUE_FLUSH, null);

        }
    };

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
