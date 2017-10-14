package com.ablethon.woongsang.gestcapturex.ProcessGesture;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.Activity.DestinationActivity;
import com.ablethon.woongsang.gestcapturex.VO.Vertex;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-13.
 */

public class ProcessDestinationGesture  extends ProcessGesture {
    String departure = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void process(ArrayList<Vertex> detectedPattern, Activity activity, Context context, boolean isCircle) {


        if (detectedPattern.size() == 1) {
            if (detectedPattern.get(0).getSection() == 2) { //위->아래로 스크롤
                departure = DestinationActivity.getNextName(1);
                DestinationActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 6) {   //위->아래로 스크롤
                departure = DestinationActivity.getNextName(2);
                DestinationActivity.myTTS.speak(departure, TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(context, departure + " ", Toast.LENGTH_LONG).show();
            } else if (detectedPattern.get(0).getSection() == 0 || detectedPattern.get(0).getSection() == 4) {       //좌->우로 스크롤
                if (DestinationActivity.itemSelector < 0) {
                    departure = DestinationActivity.getNextName(1);
                }

                CommonLibrary.DESTINATION = departure;
                Log.i("목적지선택",departure+"");

                String result="출발지는 "+CommonLibrary.DEPARTURE+"로 도착지는 "+CommonLibrary.DESTINATION+"로 설정되었습니다. 연락을 기다려 주십시오" ;
                DestinationActivity.myTTS.speak(result, TextToSpeech.QUEUE_FLUSH, null);


                new Thread() {
                    public void run() {
                        String encDeparture="";
                        String encDestination="";
                        String name="";
                        try {
                            name = java.net.URLEncoder.encode(new String("김상헌".getBytes("UTF-8")));
                             encDeparture=java.net.URLEncoder.encode(new String(CommonLibrary.DEPARTURE.getBytes("UTF-8")));
                             encDestination=java.net.URLEncoder.encode(new String(CommonLibrary.DESTINATION.getBytes("UTF-8")));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String tempUrl=CommonLibrary.MY_IP+"/groupware/request?departure="+encDeparture+"&destination="+encDestination+
                                "&name="+name+"&phone=01058781501";
  //                      String tempUrl=CommonLibrary.MY_IP+"/groupware/request?departure="+"t"+"&destination="+"b";
                        URL url;
                        Log.i("ㅇㅇㅇㅇㅇㅇ",tempUrl+"");

                        HttpURLConnection urlConnection = null;
                        try {
                            url = new URL(tempUrl);

                            urlConnection = (HttpURLConnection) url
                                    .openConnection();

                            InputStream in = urlConnection.getInputStream();

                            InputStreamReader isw = new InputStreamReader(in);

                            int data = isw.read();
                            while (data != -1) {
                                char current = (char) data;
                                data = isw.read();
                                System.out.print(current);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                        }
                    }
                }.start();


                try {
                    Thread.sleep(7000);
                    DestinationActivity.myTTS.shutdown();
                    activity.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }

        }
        for(int i=0;i<detectedPattern.size();i++){
            Log.i(i+1+"번째 움직임",detectedPattern.get(i).getSection()+"");
        }
    }
}
