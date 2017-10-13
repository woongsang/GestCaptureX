package com.ablethon.woongsang.gestcapturex.API;

import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.Activity.DepartureActivity;

import com.ablethon.woongsang.gestcapturex.Activity.DestinationActivity;
import com.ablethon.woongsang.gestcapturex.Activity.NewsActivity;

import com.ablethon.woongsang.gestcapturex.VO.Article;
import com.ablethon.woongsang.gestcapturex.VO.Location;
import com.ablethon.woongsang.gestcapturex.VO.Person;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-10.
 */

public class CommonLibrary {
    public static ArrayList<Person> PERSON_LIST = new ArrayList<Person>();
    public static ArrayList<Article> ARTICLE_LIST = new ArrayList<Article>();
    public static String DEPARTURE;
    public static String DESTINATION;
    public static String MY_IP="http://192.168.1.5:8088";

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


}
