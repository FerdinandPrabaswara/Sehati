package com.example.sehati.model;

public class MeditationModel {
    private String author;
    private String duration;
    private String photo;
    private String title;

    public MeditationModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MeditationItem.class)
    }

    public MeditationModel(String author, String duration, String photo, String title) {
        this.author = author;
        this.duration = duration;
        this.photo = photo;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
