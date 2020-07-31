package com.example.myapplication2;

import io.realm.RealmObject;

public class CategoryEnt extends RealmObject {
    private String number;

    public CategoryEnt(String number) {
        this.number = number;
    }

    public CategoryEnt(){

    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
