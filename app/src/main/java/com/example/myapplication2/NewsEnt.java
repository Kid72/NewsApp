package com.example.myapplication2;

import android.annotation.SuppressLint;

import io.realm.RealmObject;

public class NewsEnt extends RealmObject {
    private String id;
    private String last_update;
    private String modification_date;
    private int sequence;
    private int owner_site;
    private String created_date;
    private String description;
    private String lang;
    private String link;
    private String image_link;
    private String category;

    public NewsEnt(String id, String last_update, String modification_date, int sequence, int owner_site, String created_date, String description, String lang, String link, String image_link, String category) {
        this.id = id;
        this.last_update = last_update;
        this.modification_date = modification_date;
        this.sequence = sequence;
        this.owner_site = owner_site;
        this.created_date = created_date;
        this.description = description;
        this.lang = lang;
        this.link = link;
        this.image_link = image_link;
        this.category = category;
    }

    public NewsEnt(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    //java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public int getOwner_site() {
        return owner_site;
    }

    public void setOwner_site(int owner_site) {
        this.owner_site = owner_site;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
