package com.rajendra.vacationtourapp.model;

public class FoodModel {

    private int id_food;
    private String name_food;
    private String address_food;
    private Images img_food;
    private int price;
    private String rate;

    public FoodModel(int id_food, String name_food, String address_food, Images img_food, int price, String rate) {
        this.id_food = id_food;
        this.name_food = name_food;
        this.address_food = address_food;
        this.img_food = img_food;
        this.price = price;
        this.rate = rate;
    }

    public FoodModel() {
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public String getAddress_food() {
        return address_food;
    }

    public void setAddress_food(String address_food) {
        this.address_food = address_food;
    }

    public Images getImg_food() {
        return img_food;
    }

    public void setImg_food(Images img_food) {
        this.img_food = img_food;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
