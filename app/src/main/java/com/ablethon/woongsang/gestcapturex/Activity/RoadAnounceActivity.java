package com.ablethon.woongsang.gestcapturex.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ablethon.woongsang.gestcapturex.API.CommonLibrary;
import com.ablethon.woongsang.gestcapturex.API.References;
import com.ablethon.woongsang.gestcapturex.API.TouchInterface;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessCallGesture;
import com.ablethon.woongsang.gestcapturex.ProcessGesture.ProcessRoadAnounceGesture;
import com.ablethon.woongsang.gestcapturex.R;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.util.ArrayList;
import java.util.Collection;

import static android.content.ContentValues.TAG;

/**
 * Created by SangHeon on 2017-10-14.
 */

public class RoadAnounceActivity extends Activity implements TextToSpeech.OnInitListener, RECORangingListener, RECOServiceConnectListener {

    public static TextToSpeech myTTS;
    public static ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    public static int itemSelector = -1;
    Context context=this;
    int callChecker = 0;

    //for beacon
    protected RECOBeaconManager mRecoManager;
    protected ArrayList<RECOBeaconRegion> mRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anounce_road);

      //  readBeacon();
      //  CommonLibrary.initPersonList();
        itemSelector = -1;
        myTTS = new TextToSpeech(this, this);
      //  callChecker=0;

//        for(int i=0;i<CommonLibrary.PERSON_LIST.size();i++){
//            mDatas.add( CommonLibrary.PERSON_LIST.get(i).getName() );
//        }

        ArrayAdapter adapter= new ArrayAdapter(this, R.layout.bigfont_item, mDatas);

        listview= (ListView) findViewById(R.id.RoadAnounceListView);
        listview.setAdapter(adapter);


        try {
            Thread.sleep(5000);
            myTTS.speak("현재위치는 화장실 앞 입니다. 엘레베이터 20m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);

            Thread.sleep(5000);
            myTTS.speak("현재위치는 화장실 앞 입니다. 엘레베이터 12m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);

            Thread.sleep(5000);
            myTTS.speak("현재위치는 엘레베이터 앞 입니다. 강당 20m전 입니다.", TextToSpeech.QUEUE_FLUSH, null);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }






     //   listview.setOnTouchListener(scrollChecker);

    }

    AdapterView.OnTouchListener scrollChecker = new  AdapterView.OnTouchListener() {


        ProcessRoadAnounceGesture pg= new ProcessRoadAnounceGesture();                               //to prcessing gesture
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
    public static String getNextItem(int operator){
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
        String myText1 = "길 안내를 시작합니다,";

        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void onBackPressed() {
       unbind();

        Thread.interrupted();
        String myText1 = "비콘을 이용한 길안내를 종료합니다.";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(myTTS != null) {

            myTTS.stop();
            myTTS.shutdown();
            Log.d(TAG, "TTS Destroyed");
        }
        finish();

    }

    //------------------------for beacon methods
    private void readBeacon()  {

        mRecoManager = RECOBeaconManager.getInstance(context, References.SCAN_RECO_ONLY, References.ENABLE_BACKGROUND_RANGING_TIMEOUT);
        mRegions = this.generateBeaconRegion();

        mRecoManager.setRangingListener(this);
        mRecoManager.bind(this);




    }
    private ArrayList<RECOBeaconRegion> generateBeaconRegion() {
        ArrayList<RECOBeaconRegion> regions = new ArrayList<RECOBeaconRegion>();

        RECOBeaconRegion recoRegion;
        recoRegion = new RECOBeaconRegion(References.RECO_UUID, "RECO Sample Region");
        regions.add(recoRegion);

        return regions;
    }


    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("RECORangingActivity", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(References.DISCONTINUOUS_SCAN);
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for (RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    protected void start(ArrayList<RECOBeaconRegion> regions) {

        /**
         * There is a known android bug that some android devices scan BLE devices only once. (link: http://code.google.com/p/android/issues/detail?id=65863)
         * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
         * This method is to set whether the device scans BLE devices continuously or discontinuously.
         * The default is set as FALSE. Please set TRUE only for specific devices.
         *
         * mRecoManager.setDiscontinuousScan(true);
         */

        for (RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.`
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed to range beacons in the region.
        //See the RECOErrorCode in the documents.
        return;
    }

    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());


        ArrayList<RECOBeacon> rb = (ArrayList<RECOBeacon>) recoBeacons;

        if(rb.size() > 0) {
            String result = CommonLibrary.getNowLocation(rb);
            myTTS.speak(result, TextToSpeech.QUEUE_FLUSH, null);

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    }

