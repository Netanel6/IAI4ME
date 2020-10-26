package com.netanel.iaiforme.pojo;

import java.util.ArrayList;

public class Aircraft {
    private String name;
    private String model;
    private String date;
    private String timeDate;
    private String id;
    private String paka;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public Aircraft() {
    }

    public Aircraft(String name, String model) {
        this.name = name;
        this.model = model;
    }

    //Add ac fragment and aircraft list fragment
    public Aircraft(String name, String model, String date , String paka) {
        this.name = name;
        this.model = model;
        this.date = date;
        this.paka = paka;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public String getPaka() {
        return paka;
    }

    public void setPaka(String paka) {
        this.paka = paka;
    }


    @Override
    public String toString() {
        return "Aircraft{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", status=" +
                '}';
    }

}



