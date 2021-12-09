package com.rajendra.vacationtourapp.model;

public class HotelModel {

    private int id_hotel;
    private String name_hotel;
    private String address_hotel;
    private String decription;
    private int price;
    private Images img_hotel;


    public HotelModel(int id_hotel, String name_hotel, String address_hotel, String decription, int price, Images img_hotel) {
        this.id_hotel = id_hotel;
        this.name_hotel = name_hotel;
        this.address_hotel = address_hotel;
        this.decription = decription;
        this.price = price;
        this.img_hotel = img_hotel;
    }


    public HotelModel() {
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getName_hotel() {
        return name_hotel;
    }

    public void setName_hotel(String name_hotel) {
        this.name_hotel = name_hotel;
    }

    public String getAddress_hotel() {
        return address_hotel;
    }

    public void setAddress_hotel(String address_hotel) {
        this.address_hotel = address_hotel;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Images getImg_hotel() {
        return img_hotel;
    }

    public void setImg_hotel(Images img_hotel) {
        this.img_hotel = img_hotel;
    }
}
