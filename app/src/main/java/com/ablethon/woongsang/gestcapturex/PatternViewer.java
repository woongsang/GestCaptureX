package com.ablethon.woongsang.gestcapturex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PatternViewer extends View {

    static final float PI = 3.1415926535f;
    static final float RATE_TO_DEGREE = 180/PI;
    static final int NUM_OF_SECTIONS = 8;
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

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.BLUE);
        drawLine(canvas, userLine);
        paint.setColor(Color.GREEN);
        drawLine(canvas, interpolation);
        paint.setColor(Color.RED);
        drawLine(canvas, detectedPattern);
    }

    private void drawLine(Canvas canvas, ArrayList<Vertex> line) {
        for (int i = 0; i < line.size() - 1; i++){
            canvas.drawLine(line.get(i).getX(), line.get(i).getY(),
                            line.get(i+1).getX(), line.get(i+1).getY(), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){ //when the finger is touching the screen
            userLine.removeAll(userLine);
            interpolation.removeAll(interpolation);
            detectedPattern.removeAll(detectedPattern);

            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));

            return true;
        }

        else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));
            invalidate();

            return true;
        }

        else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
            float section = 2 * PI / NUM_OF_SECTIONS;
            float anglesSum = 0;
            float anglesSum2 = 0;
            float lengthsSum = 0;
            boolean resetAnglesSum = true;
            //interpolation.add(userLine.get(0));
            for (int i = 0; i < userLine.size() - 1; i++){
                float x2 = userLine.get(i + 1).getX() - userLine.get(i).getX();
                float y2 = userLine.get(i + 1).getY() - userLine.get(i).getY();

                userLine.get(i).setRadian(getAngle(x2, y2));
                userLine.get(i).setLength((float)Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f)));
                userLine.get(i).setSection ((userLine.get(i).getRadian() + (section/2)) % (2*PI));

                if (resetAnglesSum) {
                    resetAnglesSum = false;
                }

                else{
                    float diff = userLine.get(i).getRadian() - userLine.get(i+1).getRadian();
                    if (diff > PI){
                        diff -= 2*PI;
                    }
                    else if (diff < -PI){
                        diff += 2*PI;
                    }
                    anglesSum += diff;
                }

                lengthsSum += userLine.get(i).getLength();

                if (!(-section*3/2 <= anglesSum && anglesSum <= section*3/2)){
                    resetAnglesSum = false;
                    anglesSum2 = anglesSum;
                    anglesSum = 0;
                    interpolation.add(userLine.get(i));
                }
            }
            interpolation.add(userLine.get(userLine.size()-1));

            if (anglesSum2 > MIN_ROUND_ANGLE || MIN_ROUND_ANGLE < -anglesSum2){
                return false;
            }

            float movedAngle = 0;
            for (int i = 0; i < interpolation.size()-1; i++){
                float x2 = interpolation.get(i+1).getX() - interpolation.get(i).getX();
                float y2 = interpolation.get(i).getY() - interpolation.get(i+1).getY();
                float length = (float)Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                if (length < lengthsSum/interpolation.size()/2) {
                    continue;
                }

                float radian = getAngle(x2, y2);
                radian += movedAngle;

                float angle = (radian + (section/2)) % (2 * PI);

                float angleToMove = angle % section;
                angleToMove = section/2 - angleToMove;
                if (i == 0){
                    movedAngle = angleToMove;
                }
                int sec = (int)(angle/section);
                if(detectedPattern.size() > 0){
                    if (detectedPattern.get(detectedPattern.size()-1).getSection() == sec){
                        detectedPattern.get(detectedPattern.size()-1).addLength(length);
                        continue;
                    }
                }
                Vertex vertex = new Vertex(interpolation.get(i).getX(), interpolation.get(i).getY());
                vertex.setRadian(radian+angleToMove);
                vertex.setLength(length);
                vertex.setSection(sec);
                detectedPattern.add(vertex);
            }
            invalidate();
            return true;
        }
        return false;
    }

    private float getAngle(float x2, float y2) {
        float x1 = 1.f;
        float y1 = 0.f;

        if (x1 == x2){
            x2 *= 2;
            y2 *= 2;
        }

        float radian = -(float)Math.atan((y2-y1)/(x2-x1));

        if (x2 < 0 && y2 == 0){
            radian -= PI;
        }

        if (x1 > x2) {
            radian += PI;
        }
        else if (x1 < x2 && y1 < y2) {
            radian += 2*PI;
        }

        return radian;
    }
}
