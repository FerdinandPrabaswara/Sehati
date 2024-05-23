package com.example.sehati;

public class MeditationModel {
    String meditationName;
    String meditationDescription;
    int image;


    public MeditationModel(String meditationName, String meditationDescription, int image) {
        this.meditationName = meditationName;
        this.meditationDescription = meditationDescription;
        this.image = image;
    }


    public String getMeditationName() {
        return meditationName;
    }

    public String getMeditationDescription() {
        return meditationDescription;
    }

    public int getImage() {
        return image;
    }
}
