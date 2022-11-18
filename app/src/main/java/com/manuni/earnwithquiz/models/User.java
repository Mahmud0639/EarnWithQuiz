package com.manuni.earnwithquiz.models;

public class User {
    private String name, email, pass, profile, referCode,adminMessage = "";
    private double coins = 0.00;

    public User() {
    }

    public User(String name, String email, String pass, String referCode) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.referCode = referCode;
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
    

/*    public String getCoins_taka(){
        return coins_taka;
    }

    public void setCoins_taka(String coins_taka){
        this.coins_taka = coins_taka;
    }*/


}
