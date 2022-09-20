package com.example.foodapp;

public class MainModel {

    String food_name,furl,price;    //database variables declaration

    //constructor without parameters
    MainModel(){

    }

    //overloaded constructor
    public MainModel(String food_name, String furl, String price) {
        this.food_name = food_name;
        this.furl = furl;
        this.price = price;
    }

    //getters and setters
    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
