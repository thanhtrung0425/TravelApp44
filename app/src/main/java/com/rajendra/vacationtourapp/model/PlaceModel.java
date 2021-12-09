package com.rajendra.vacationtourapp.model;

public class PlaceModel {

    private int id_place;
    private String name_place;
    private String address_place;
    private String decription;
    private Images img_place;

    public PlaceModel(int id_place, String name_place, String address_place, String decription, Images img_place) {
        this.id_place = id_place;
        this.name_place = name_place;
        this.address_place = address_place;
        this.decription = decription;
        this.img_place = img_place;
    }

    public PlaceModel() {
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }

    public String getName_place() {
        return name_place;
    }

    public void setName_place(String name_place) {
        this.name_place = name_place;
    }

    public String getAddress_place() {
        return address_place;
    }

    public void setAddress_place(String address_place) {
        this.address_place = address_place;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Images getImg_place() {
        return img_place;
    }

    public void setImg_place(Images img_place) {
        this.img_place = img_place;
    }
}
