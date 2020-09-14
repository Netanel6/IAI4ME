package com.netanel.iaiforme.pojo;

import java.util.ArrayList;


public class User {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String last;
    private String phone;
    private String personalNumber;
    private String status;
    private String profilePicUrl;
    private ArrayList<String> tokens;

    public User() {
    }

    public User(String profilePicUrl){
        this.profilePicUrl = profilePicUrl;
    }
    public User(String uid, String email, String password, String name,
                String last, String phone, String personalNumber,
                String status,String profilePicUrl, ArrayList<String> tokens) {

        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.last = last;
        this.phone = phone;
        this.personalNumber = personalNumber;
        this.status = status;
        this.profilePicUrl = profilePicUrl;
        this.tokens = new ArrayList<>();
        this.tokens.add("A");
        this.tokens.add("B");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return name + " " + last;
    }



}

