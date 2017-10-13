package com.ablethon.woongsang.gestcapturex.API;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessGesture;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;


import java.io.IOException;
import java.util.ArrayList;

public class TouchInterface {

    public TouchInterface(Activity activity,Context context,ProcessGesture pg){
        this.mParent = activity;
        this.context=context;
        this.pg = pg;
        initialize();
    }
    MotionEvent motionEvent;
    Activity mParent;
    ProcessGesture pg;
    static final float PI = 3.1415926535f;
    //static final float RATE_TO_DEGREE = 180/PI;
    static final int NUM_OF_SECTIONS = 8;
    static final float MIN_ROUND_ANGLE = 2*PI* 11/12;

    static final int BACKGROUND_COLOR = Color.WHITE;
    static final int LINE_THICKNESS = 15;

    Context context;
    ArrayList<Vertex> detectedPattern;
    ArrayList<Vertex> userLine;
    ArrayList<Vertex> interpolation;

    public boolean gestureInterface(MotionEvent motionEvent){
        this.motionEvent=motionEvent;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){ //when the finger is touching the screen
            userLine.clear();
            interpolation.clear();
            detectedPattern.clear();

            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));

            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));


            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP){
            float section = 2 * PI / NUM_OF_SECTIONS;
            float anglesSum = 0;
            float anglesSum2 = 0;
            float lengthsSum = 0;
            boolean resetFlag = true;

            interpolation.add(userLine.get(0));

            for (int i = 1; i < userLine.size() ; i++){
                float x2 = userLine.get(i).getX() - userLine.get(i-1).getX();
                float y2 = userLine.get(i - 1).getY() - userLine.get(i).getY();

                float radian = getAngle(x2, y2);

                float length = (float)Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));

                float angle = (radian + section/2) % (2*PI);
                int sec = (int)(angle / section);

                userLine.get(i).setRadian(radian);
                userLine.get(i).setLength(length);
                userLine.get(i).setSection(sec);

                if (resetFlag) {
                    resetFlag = false;
                }

                else{
                    float diff = userLine.get(i-1).getRadian() - userLine.get(i).getRadian();
                    if (diff > PI){
                        diff -= 2*PI;
                    }
                    else if (diff < -PI){
                        diff += 2*PI;
                    }
                    anglesSum += diff;
                    anglesSum2 += diff;
                }

                lengthsSum += length;

                if (-section*3/2 > anglesSum || anglesSum > section*3/2){
                    resetFlag = false;
                    anglesSum = 0;
                    interpolation.add(userLine.get(i));
                }
            }
            interpolation.add(userLine.get(userLine.size()-1));

            if(anglesSum2 > MIN_ROUND_ANGLE){
                int round = (int) (anglesSum2 / (2*PI));
                if (anglesSum2 % (2*PI) > MIN_ROUND_ANGLE){
                    round++;
                }
                //반시계방향 원 round 바퀴
                return false;
            }

            else if (-anglesSum2 > MIN_ROUND_ANGLE){
                int round = (int) (-anglesSum2 / (2*PI));
                if (-anglesSum2 % (2*PI) > MIN_ROUND_ANGLE){
                    round++;
                }
                //시계방향 원 round 바퀴
                return false;
            }

            float movedAngle = 0;
            for (int i = 1; i < interpolation.size(); i++){
                float x2 = interpolation.get(i).getX() - interpolation.get(i-1).getX();
                float y2 = interpolation.get(i-1).getY() - interpolation.get(i).getY();
                float length = (float)Math.sqrt(Math.pow(x2, 2.f) + Math.pow(y2, 2.f));
                if (length < lengthsSum/interpolation.size()/2) {
                    continue;
                }

                float radian = getAngle(x2, y2);
                radian += movedAngle;

                float angle = (radian + section/2) % (2 * PI);

                float angleToMove = angle % section;
                angleToMove = section/2 - angleToMove;
                if (i == 1){
                    movedAngle = angleToMove;
                }
                int sec = (int)(angle/section);
                if(detectedPattern.size() > 0){
                    if (detectedPattern.get(detectedPattern.size()-1).getSection() == sec){
                        detectedPattern.get(detectedPattern.size()-1).addLength(length);
                        continue;
                    }
                }
                Vertex vertex = new Vertex(interpolation.get(i-1).getX(), interpolation.get(i-1).getY());
                vertex.setRadian(radian+angleToMove);
                vertex.setLength(length);
                vertex.setSection(sec);
                detectedPattern.add(vertex);
            }

            String str = "detected-pattern: ";
            for(int i=0; i<detectedPattern.size(); i++)
            {
                str += detectedPattern.get(i).getSection();
                if( i < detectedPattern.size()-1 )
                    str = str + " -> ";
            }

            pg.process( detectedPattern, mParent, context );

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

        if (x1 > x2 && y1 != y2) {
            radian += PI;
        }
        else if (x1 < x2 && y1 < y2) {
            radian += 2*PI;
        }
        return radian;
    }
    private void initialize() {
        userLine = new ArrayList<>();
        interpolation = new ArrayList<>();
        detectedPattern = new ArrayList<>();
    }
}