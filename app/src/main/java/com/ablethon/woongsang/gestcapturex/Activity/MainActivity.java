package com.ablethon.woongsang.gestcapturex.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.ablethon.woongsang.gestcapturex.API.PatternViewer;

public class MainActivity extends Activity {
    PatternViewer patternViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patternViewer = new PatternViewer(this);
        setContentView(patternViewer);
    }
}