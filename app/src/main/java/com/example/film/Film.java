package com.example.film;

class Film {

    private String mTitle;
    private String mUrl;
    private String mLanguage;
    private String mGenres;
    private int mYear;
    private int mRunTime;
    private double mRating;

    Film(String title, int year, double rating, int runTime, String url, String language, String genres) {
        this.mTitle = title;
        this.mYear = year;
        this.mRating = rating;
        this.mRunTime = runTime;
        this.mUrl = url;
        this.mLanguage = language;
        this.mGenres = genres;
    }

    String getTitle() {
        return mTitle;
    }

    String getUrl() {
        return mUrl;
    }

    String getLanguage() {
        return mLanguage;
    }

    String getGenres() {
        return mGenres;
    }

    int getYear() {
        return mYear;
    }

    double getRating() {
        return mRating;
    }

    String getRunTime() {

        long timeSec = mRunTime;
        int time = (int) timeSec / 3600;
        int temp = (int) timeSec - time * 3600;
        int hours = temp / 60;
        temp = temp - hours * 60;
        int mins = temp;

        return hours + "hr " + mins + "min";
    }
}