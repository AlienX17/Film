package com.example.film;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class FilmLoader extends AsyncTaskLoader<List<Film>> {

    private String filmUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    FilmLoader(Context context, String filmUrl) {
        super(context);
        this.filmUrl = filmUrl;
    }

    @Override
    public List<Film> loadInBackground() {
        if(filmUrl == null) {
            return null;
        }

        return QueryUtils.fetchFilmData(filmUrl);
    }

}
