package com.example.ruslan.projectcinema.dbt;

/**
 * Created by ruslan on 20.01.16.
 */
public class TimetableHelper {
    private String dateTime;
    private String movie;
    private String hall;
    private String theatre;
    private String price;

    public TimetableHelper(String dateTime, String movie, String hall, String theatre, String price) {
        this.dateTime = dateTime;
        this.movie = movie;
        this.hall = hall;
        this.theatre = theatre;
        this.price = price;
    }


    public String getDateTime() {
        return dateTime;
    }

    public String getMovie() {
        return movie;
    }

    public String getHall() {
        return hall;
    }

    public String getTheatre() {
        return theatre;
    }

    public String getPrice() {
        return price;
    }
}
