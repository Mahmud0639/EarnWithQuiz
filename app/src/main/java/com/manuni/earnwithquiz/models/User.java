package com.manuni.earnwithquiz.models;

public class User {
    private String name, email, pass, profile, referCode,adminMessage = "",bKashNumber,uId,status = "No limit";
    private double coins = 0.00;

    public User() {
    }

    public User(String name, String email, String pass, String referCode,String bKashNumber,String uId) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.referCode = referCode;
        this.bKashNumber = bKashNumber;
        this.uId = uId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAdminMessage(){
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage){
        this.adminMessage = adminMessage;
    }

    public String getbKashNumber() {
        return bKashNumber;
    }

    public void setbKashNumber(String bKashNumber) {
        this.bKashNumber = bKashNumber;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
