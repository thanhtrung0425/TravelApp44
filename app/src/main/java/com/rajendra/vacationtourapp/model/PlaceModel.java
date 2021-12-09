package com.rajendra.vacationtourapp.model;

public class PlaceModel {

    private int id;
    private String placeName;
    private String addressName;
    private String decreptions;
    private Images imageUrl;

    public PlaceModel(int id, String placeName, String addressName, String decreptions, Images imageUrl) {
        this.id = id;
        this.placeName = placeName;
        this.addressName = addressName;
        this.decreptions = decreptions;
        this.imageUrl = imageUrl;
    }

    public PlaceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getDecreptions() {
        return decreptions;
    }

    public void setDecreptions(String decreptions) {
        this.decreptions = decreptions;
    }

    public Images getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Images imageUrl) {
        this.imageUrl = imageUrl;
    }
}
