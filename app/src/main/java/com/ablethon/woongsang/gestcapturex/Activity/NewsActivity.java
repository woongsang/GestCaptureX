package com.ablethon.woongsang.gestcapturex.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessNewsGesture;
import com.ablethon.woongsang.gestcapturex.R;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-12.
 */

public class NewsActivity  extends Activity implements TextToSpeech.OnInitListener {

    public static TextToSpeech myTTS;

    public static ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    public static int itemSelector = -1;
    Context context=this;
    int callChecker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        CommonLibrary.initArticleList();
        itemSelector = -1;
        myTTS = new TextToSpeech(this, this);
        callChecker=0;

        for(int i=0;i<CommonLibrary.ARTICLE_LIST.size();i++){
            mDatas.add( CommonLibrary.ARTICLE_LIST.get(i).getTitle() );
        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView) findViewById(R.id.NewsListView);
        listview.setAdapter(adapter);

        listview.setOnTouchListener(scrollChecker);

    }

    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessNewsGesture pg= new ProcessNewsGesture();                               //to prcessing gesture
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
    public static String getNextContent(int operator){
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
        String myText1 = "뉴스기사를 선택하려면 위 아래로 드래그해주세요";
        String myText2 = "해당 뉴스를 들으시려면 좌 우로 드래그해주세요.";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        myTTS.speak(myText2, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
