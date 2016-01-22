package com.example.ruslan.projectcinema.DB;

import java.io.Serializable;


public class Movie implements Serializable {
    private int _id;
    private String title;
    private String description;
    private String imageUrl;
    private String trailer;

    public Movie(int id, String title, String description, String imageUrl, String trailer) {
        _id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.trailer = trailer;
    } public Movie(String title, String description, String imageUrl, String trailer) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.trailer = trailer;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}

