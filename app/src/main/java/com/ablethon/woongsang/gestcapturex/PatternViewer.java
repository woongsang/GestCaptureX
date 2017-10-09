package com.ablethon.woongsang.gestcapturex;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PatternViewer extends View {

    static final float PI = 3.1415926535f;
    static final float RATE_TO_DEGREE = 180/PI;
    static final int NUM_OF_TYPES = 8;
    static final float MIN_ROUND_ANGLE = 2*PI*(11/12);

    static final int BACKGROUND_COLOR = Color.BLACK;
    static final int LINE_THICKNESS = 15;

    static Context context;

    ArrayList<Vertex> userLine;
    ArrayList<Vertex> interpolation;
    ArrayList<Vertex> detectedPattern;

    Paint paint;
    TextView textView;

    public PatternViewer(Context context){
        super(context);
        this.context = context;
        initialize();
    }

    private void initialize() {
        setBackgroundColor(BACKGROUND_COLOR);

        userLine = new ArrayList<Vertex>();
        interpolation = new ArrayList<Vertex>();
        detectedPattern = new ArrayList<Vertex>();

        paint = new Paint();
        paint.setStrokeWidth(LINE_THICKNESS);

        textView = new TextView(this.getContext());
    }


}
