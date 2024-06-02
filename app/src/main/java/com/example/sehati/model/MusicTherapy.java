package com.example.sehati.model;

public class MusicTherapy {
    private String photo, title, author, duration;

    public MusicTherapy() {}

    public MusicTherapy (String inPhoto, String inTitle, String inAuthor, String inDuration) {
        this.photo = inPhoto;
        this.author = inAuthor;
        this.title = inTitle;
        this.duration = inDuration;
    }

    public String getAuthor() {
        return author;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }
}
