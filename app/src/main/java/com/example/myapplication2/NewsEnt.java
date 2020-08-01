package com.example.myapplication2;

import android.annotation.SuppressLint;

import io.realm.RealmObject;

public class NewsEnt extends RealmObject {
    private String id;
    private int orderid;
    private String modification_date;
    private String time_stamp;
    private int owner_site;
    private String descr;
    private String lang;
    private String link;
    private String image_link;
    private String category;
    private String oper_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public int getOwner_site() {
        return owner_site;
    }

    public void setOwner_site(int owner_site) {
        this.owner_site = owner_site;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOper_time() {
        return oper_time;
    }

    public void setOper_time(String oper_time) {
        this.oper_time = oper_time;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public NewsEnt(String id, int orderid, String modification_date, String time_stamp, int owner_site, String descr, String lang, String link, String image_link, String category, String oper_time) {
        this.id = id;
        this.orderid = orderid;
        this.modification_date = modification_date;
        this.time_stamp = time_stamp;
        this.owner_site = owner_site;
        this.descr = descr;
        this.lang = lang;
        this.link = link;
        this.image_link = image_link;
        this.category = category;
        this.oper_time = oper_time;
    }

    public NewsEnt() {

    }
}
