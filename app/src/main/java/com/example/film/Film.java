package com.example.film;

class Film {

    private String mTitle;
    private int mYear;
    private double mRating;
    private int mRunTime;
    private String mUrl;

    Film(String title, int year, double rating, int runtime, String url) {
        this.mTitle = title;
        this.mYear = year;
        this.mRating = rating;
        this.mRunTime = runtime;
        this.mUrl = url;
    }

    String getTitle() {
        return mTitle;
    }

    int getYear() {
        return mYear;
    }

    double getRating() {
        return mRating;
    }

    int getRunTime() {
        return mRunTime;
    }

    String getUrl() {
        return mUrl;
    }

}