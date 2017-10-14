package com.ablethon.woongsang.gestcapturex.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.ablethon.woongsang.gestcapturex.API.PatternViewer;
import com.ablethon.woongsang.gestcapturex.R;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    PatternViewer patternViewer;
    public static TextToSpeech myTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTTS = new TextToSpeech(this, this);

        patternViewer = new PatternViewer(this);
        patternViewer.setBackground(getResources().getDrawable(R.drawable.mainbg));
        setContentView(patternViewer);
    }

    @Override
    public void onInit(int status) {
        myTTS.speak("음성안내를 시작합니다.", TextToSpeech.QUEUE_FLUSH, null);
    }
}