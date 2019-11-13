package com.example.film;

class JsonModel {

    private int id;
    private String url;
    private String title;
    private String title_english;
    private int year;
    private float rating;
    private int runtime;


    //ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //URL
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //TITLE
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //EN TITLE
    public String getTitleEN() {
        return title_english;
    }

    public void setTitleEN(String title_english) {
        this.title_english = title_english;
    }

    //YEAR
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //RATING
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //RUNTIME
    public int getRunTime() {
        return runtime;
    }

    public void setRunTime(int runtime) {
        this.runtime = runtime;
    }
}