package com.ablethon.woongsang.gestcapturex;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {
    PatternViewer patternViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patternViewer = new PatternViewer(this);
        setContentView(patternViewer);
    }
}
