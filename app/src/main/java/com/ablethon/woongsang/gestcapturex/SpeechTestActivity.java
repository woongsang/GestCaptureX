package com.ablethon.woongsang.gestcapturex;

/**
 * Created by SangHeon on 2017-10-10.
 */

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class SpeechTestActivity extends Activity implements OnInitListener {

    private TextToSpeech myTTS;

    ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    CommonLibrary commonLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_test);

        CommonLibrary.initPersonList();

        for(int i=0;i<CommonLibrary.PERSON_LIST.size();i++){
            mDatas.add( CommonLibrary.PERSON_LIST.get(i).getName() );
        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView)findViewById(R.id.listview);

        listview.setAdapter(adapter);


        listview.setOnItemLongClickListener(longClickListener);
        listview.setOnItemClickListener(listener);



        myTTS = new TextToSpeech(this, this);
    }


    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = mDatas.get(position);
            String phone =  CommonLibrary.getPhoneNumber(name);
            myTTS.speak("짧게누름"+name+phone, TextToSpeech.QUEUE_FLUSH, null);

        }
    };
    /*
    *  longClickListener
    *
    *  */
    OnItemLongClickListener longClickListener= new OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            String name = mDatas.get(position);
            String phone =  CommonLibrary.getPhoneNumber(name);
            myTTS.speak("길게누름"+name+phone, TextToSpeech.QUEUE_FLUSH, null);


            return false;
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
