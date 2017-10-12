package com.ablethon.woongsang.gestcapturex.API;

import com.ablethon.woongsang.gestcapturex.Activity.CallActivity;
import com.ablethon.woongsang.gestcapturex.VO.Person;

import java.util.ArrayList;

/**
 * Created by SangHeon on 2017-10-10.
 */

public class CommonLibrary {
    public static ArrayList<Person> PERSON_LIST = new ArrayList<Person>();

    public static void initPersonList(){
        PERSON_LIST.clear();
        CallActivity.mDatas.clear();

        PERSON_LIST.add(new Person("정웅섭","01096665001"));
        PERSON_LIST.add(new Person("김상헌","01058781501"));
        PERSON_LIST.add(new Person("박지성","123456789"));
        PERSON_LIST.add(new Person("이영표","987654321"));
        PERSON_LIST.add(new Person("차두리","000000000"));
        PERSON_LIST.add(new Person("홍명보","111111111"));
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
