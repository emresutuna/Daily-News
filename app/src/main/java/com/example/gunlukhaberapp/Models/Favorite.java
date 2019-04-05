package com.example.gunlukhaberapp.Models;

public class Favorite {
    String id;
    String photo;
    String title;
    String description;
    String date;
    String url;

    public Favorite(String id, String photo, String title, String description, String date, String url) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
