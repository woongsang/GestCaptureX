package com.ablethon.woongsang.gestcapturex;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.Vertex;

import java.util.ArrayList;

public class PatternViewer extends View {

    Activity mParent;

    static final float PI = 3.1415926535f;
    //static final float RATE_TO_DEGREE = 180/PI;
    static final int NUM_OF_SECTIONS = 8;
    static final float MIN_ROUND_ANGLE = 2*PI* 11/12;

    static final int BACKGROUND_COLOR = Color.WHITE;
    static final int LINE_THICKNESS = 15;

    Context context;

    ArrayList<Vertex> userLine;
    ArrayList<Vertex> interpolation;
    ArrayList<Vertex> detectedPattern;

    Paint paint;

    public PatternViewer(Context context){
        super(context);
        this.context = context;
        mParent=(Activity)context;
        initialize();
    }

    private void initialize() {
        setBackgroundColor(BACKGROUND_COLOR);

        userLine = new ArrayList<>();
        interpolation = new ArrayList<>();
        detectedPattern = new ArrayList<>();

        paint = new Paint();
        paint.setStrokeWidth(LINE_THICKNESS);
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        drawLine(canvas, userLine);
        paint.setColor(Color.GREEN);
        paint.setAlpha(50);
        drawLine(canvas, interpolation);
        paint.setColor(Color.RED);
        drawPattern(canvas);
    }

    private void drawLine(Canvas canvas, ArrayList<Vertex> line) {
        for (int i = 1; i < line.size(); i++){
            canvas.drawLine(line.get(i-1).getX(), line.get(i-1).getY(),
                            line.get(i).getX(), line.get(i).getY(), paint);
        }
    }

    private void drawPattern(Canvas canvas) {
        for (Vertex v : detectedPattern){
            float x1 = v.getX();
            float y1 = v.getY();
            float x2 = x1 + v.getLength() * (float)Math.cos(v.getRadian());
            float y2 = y1 + v.getLength() * (float)Math.sin(v.getRadian());
            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){ //when the finger is touching the screen
            userLine.clear();
            interpolation.clear();
            detectedPattern.clear();

            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));

            return true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            userLine.add(new Vertex(motionEvent.getX(), motionEvent.getY()));
            invalidate();

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
                Toast.makeText(this.getContext(), "원(반시계방향) "+ round + "바퀴" , Toast.LENGTH_SHORT).show();
                return false;
            }

            else if (-anglesSum2 > MIN_ROUND_ANGLE){
                int round = (int) (-anglesSum2 / (2*PI));
                if (-anglesSum2 % (2*PI) > MIN_ROUND_ANGLE){
                    round++;
                }
                Toast.makeText(this.getContext(), "원(시계방향) "+ round + "바퀴" , Toast.LENGTH_SHORT).show();
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
            invalidate();
            String str = "detected-pattern: ";
            for(int i=0; i<detectedPattern.size(); i++)
            {
                str += detectedPattern.get(i).getSection();
                if( i < detectedPattern.size()-1 )
                    str = str + " -> ";
            }
            ProcessGesture pg= new ProcessGesture();
            pg.processMainGesture( detectedPattern, mParent, context );

            Toast.makeText(this.getContext(), str, Toast.LENGTH_SHORT).show();
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
}
