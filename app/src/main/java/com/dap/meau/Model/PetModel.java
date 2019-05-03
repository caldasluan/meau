package com.dap.meau.Model;

import java.io.Serializable;

public class PetModel implements Serializable {
    private String name, gender, age, postage, city, imageUrl, disease, temperament, requiriments, about;
    private boolean castrated, dewormed, vaccinated;

    public PetModel(String name, String gender, String age, String postage, String city, String imageUrl) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.postage = postage;
        this.city = city;
        this.imageUrl = imageUrl;
    }

    public PetModel(String name, String gender, String age, String postage, String city, String imageUrl, String disease, String temperament, String requiriments, String about, boolean castrated, boolean dewormed, boolean vaccinated) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.postage = postage;
        this.city = city;
        this.imageUrl = imageUrl;
        this.disease = disease;
        this.temperament = temperament;
        this.requiriments = requiriments;
        this.about = about;
        this.castrated = castrated;
        this.dewormed = dewormed;
        this.vaccinated = vaccinated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getRequiriments() {
        return requiriments;
    }

    public void setRequiriments(String requiriments) {
        this.requiriments = requiriments;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isCastrated() {
        return castrated;
    }

    public void setCastrated(boolean castrated) {
        this.castrated = castrated;
    }

    public boolean isDewormed() {
        return dewormed;
    }

    public void setDewormed(boolean dewormed) {
        this.dewormed = dewormed;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
