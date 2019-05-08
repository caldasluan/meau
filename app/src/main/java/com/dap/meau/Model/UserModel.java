package com.dap.meau.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String uid, shortName, fullName, email, state, city, address, username, password, phone, imageUrl, history;
    private int age;

    public UserModel(String shortName, String fullName, String email, String state, String city, String address, String username, String phone, String imageUrl, String history, int age) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.email = email;
        this.state = state;
        this.city = city;
        this.address = address;
        this.username = username;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.history = history;
        this.age = age;
    }

    public UserModel(String uid, String shortName, String fullName, String email, String state, String city, String address, String username, String password, String phone, String imageUrl, String history, int age) {
        this.uid = uid;
        this.shortName = shortName;
        this.fullName = fullName;
        this.email = email;
        this.state = state;
        this.city = city;
        this.address = address;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.history = history;
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
