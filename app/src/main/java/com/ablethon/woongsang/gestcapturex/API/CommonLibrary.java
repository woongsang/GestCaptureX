package com.ablethon.woongsang.gestcapturex.API;

import android.util.Log;

import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.DepartureActivity;

import com.ablethon.woongsang.gestcapturex.Activity.DestinationActivity;
import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;

import com.ablethon.woongsang.gestcapturex.VO.Article;
import com.ablethon.woongsang.gestcapturex.VO.Location;
import com.ablethon.woongsang.gestcapturex.VO.Person;
import com.perples.recosdk.RECOBeacon;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-10.
 */

public class CommonLibrary {
    public static ArrayList<Person> PERSON_LIST = new ArrayList<Person>();
    public static ArrayList<Article> ARTICLE_LIST = new ArrayList<Article>();
    public static String DEPARTURE;
    public static String DESTINATION;
    public static String MY_IP="http://192.168.51.51:8088";
    public static String preLoc="" ;
    public static String curLoc="";
    public static int preMeter=500;
    public static int curMeter=500;

    public static void insertArticle(String title,String description){
        ARTICLE_LIST.add(new Article(title,description));
    }

    public static void initDepartureList(){
        DepartureActivity.mDatas.add("상도초등학교");
        DepartureActivity.mDatas.add("송내역");
        DepartureActivity.mDatas.add("복사골문화센터");
        DepartureActivity.mDatas.add("하얀마을");
        DepartureActivity.mDatas.add("현대백화점");
        DepartureActivity.mDatas.add("부천시청역");
        DepartureActivity.mDatas.add("부명고등학교");

    }


    public static void initDestinationList(){
        DestinationActivity.mDatas.add("상도초등학교");
        DestinationActivity.mDatas.add("송내역");
        DestinationActivity.mDatas.add("복사골문화센터");
        DestinationActivity.mDatas.add("하얀마을");
        DestinationActivity.mDatas.add("현대백화점");
        DestinationActivity.mDatas.add("부천시청역");
        DestinationActivity.mDatas.add("부명고등학교");

    }
    public static String getArticleDescription(String title){

        for(int i=0;i< ARTICLE_LIST.size();i++){
            if(ARTICLE_LIST.get(i).getTitle().equals(title)){
                return ARTICLE_LIST.get(i).getDescription();
            }
        }
        return "No Content";
    }

    public static void initPersonList(){
        PERSON_LIST.clear();
        CallActivity.mDatas.clear();

        PERSON_LIST.add(new Person("정웅섭","01096665001"));
        PERSON_LIST.add(new Person("김상헌","01058781501"));
        PERSON_LIST.add(new Person("박지성","123456789"));
        PERSON_LIST.add(new Person("이영표","987654321"));
        PERSON_LIST.add(new Person("차두리","000000000"));
        PERSON_LIST.add(new Person("바우처 택시","0220920000"));
        PERSON_LIST.add(new Person("119","119"));
        PERSON_LIST.add(new Person("112","112"));
    }

    public static String getPhoneNumber(String name){

        for(int i=0;i< PERSON_LIST.size();i++){
            if(PERSON_LIST.get(i).getName().equals(name)){
                return PERSON_LIST.get(i).getPhone();
            }
        }
        return "No Phone";
    }

    public static String getNowLocation(ArrayList<RECOBeacon> result){
        String res="";
        int maxRSSI = -200;
        int maxIndex = -1;
        int accuracy;
        int maxMinor;

        for(int i=0;i<result.size();i++){
            if(maxRSSI < result.get(i).getRssi()){
                maxRSSI = result.get(i).getRssi();
                maxIndex = i;
            }
        }

        for(int i=0;i<result.size();i++){
            Log.i(i+"비콘의 신호:"+result.get(i).getRssi()," 마이너:"+result.get(i).getMinor());
        }
        maxMinor=result.get(maxIndex).getMinor();
        accuracy = (int)result.get(maxIndex).getAccuracy();


        Log.i("최고의 마이너",maxMinor+"최고 가까운거리"+accuracy);

        curMeter=accuracy;
        curLoc = getLocation(maxMinor);




        res= "현재 제일 가까운 장소는"+curLoc+"이며 도착 "+curMeter+"미터 전 입니다.";
        return res;
    }
    public static String getLocation(int minor){

        if(minor==16692){
            return "컨벤션룸1";
        } else if(minor==16693){
            return "복도";
        }else if(minor==16694){
            return "화장실";
        }else if(minor==16695){
            return "계단";
        }else if(minor==16696){
            return "대기실";
        }else if(minor==16697){
            return "엘레베이터";
        }else if(minor==16698){
            return "2층정문";
        }else if(minor==16699){
            return "대강당";
        }else if(minor==16700){
            return "휴게실";
        }else if(minor==16701){
            return "2층화장실";
        }else if(minor==16702){
            return "1층화장실";
        }


        return "알수없습니다";
    }


}
