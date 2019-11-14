package com.example.film;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class FilmLoader extends AsyncTaskLoader<List<Film>> {

    private String filmurl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    FilmLoader(Context context, String filmurl) {
        super(context);
        this.filmurl = filmurl;
    }

    @Override
    public List<Film> loadInBackground() {
        if(filmurl == null) {
            return null;
        }

        return QueryUtils.fetchFilmData(filmurl);
    }

}
