package com.rajendra.vacationtourapp.model;

public class Review {

    private int id_review;
    private int id_food;
    private String namePerson;
    private String review;
    private String rate_review;
    private String image_reviewer;

    public Review(int id_review, int id_food, String namePerson, String review, String rate_review, String image_reviewer) {
        this.id_review = id_review;
        this.id_food = id_food;
        this.namePerson = namePerson;
        this.review = review;
        this.rate_review = rate_review;
        this.image_reviewer = image_reviewer;
    }


    public Review() {
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRate_review() {
        return rate_review;
    }

    public void setRate_review(String rate_review) {
        this.rate_review = rate_review;
    }

    public String getImage_reviewer() {
        return image_reviewer;
    }

    public void setImage_reviewer(String image_reviewer) {
        this.image_reviewer = image_reviewer;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

}
