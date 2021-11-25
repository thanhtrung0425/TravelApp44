package com.rajendra.vacationtourapp.model;

public class PlaceModel {

    String placeName;
    String addressName;
    String decreptions;
    Integer imageUrl;

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PlaceModel(String placeName, String addressName, String decreptions, Integer imageUrl) {
        this.placeName = placeName;
        this.addressName = addressName;
        this.decreptions = decreptions;
        this.imageUrl = imageUrl;
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
}
