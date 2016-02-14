package com.example.test.mobilesafe.entity;

/**
 * Created by test on 2/14/2016.
 */
public class BlackList {
    private String name;
    private String tel_number;

    public BlackList(String name, String tel_number) {
        this.name = name;
        this.tel_number = tel_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }
}
