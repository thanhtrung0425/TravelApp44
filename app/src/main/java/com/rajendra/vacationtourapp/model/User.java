package com.rajendra.vacationtourapp.model;

public class User {

    private String user_name;
    private String email;
    private String password;
    private String country;
    private String phone;

    public User(String user_name, String email, String password, String country, String phone) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.country = country;
        this.phone = phone;
    }

    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
