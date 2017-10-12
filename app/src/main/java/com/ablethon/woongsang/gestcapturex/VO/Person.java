package com.ablethon.woongsang.gestcapturex.VO;

/**
 * Created by SangHeon on 2017-10-10.
 */

public class Person {
    private String name;
    private String phone;

    public Person(String name, String phone){
        this.name=name;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
