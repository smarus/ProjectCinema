package com.sunrise.cinema.dbt;

import java.io.Serializable;

/**
 * Created by ruslan on 13.01.16.
 */
public class TimeTable implements Serializable {
    private int id;
    private String hall;
    private String movie_title;
    private String theatre;
    private String date;
    private String price;
    private String codeid;

    public TimeTable( String hall, String movie_title, String theatre, String date, String price,String codeid) {
        this.hall = hall;
        this.movie_title = movie_title;
        this.theatre = theatre;
        this.date = date;
        this.price = price;
        this.codeid = codeid;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public void setTitle(String title) {
        this.movie_title = title;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {

        return id;
    }

    public String getHall() {
        return hall;
    }

    public String getTitle() {
        return movie_title;
    }

    public String getTheatre() {
        return theatre;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
