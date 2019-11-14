package com.example.film;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

class FilmAdapter extends ArrayAdapter<Film> {

    private List<Film> Films;

    FilmAdapter(@NonNull Context context, @NonNull List<Film> film) {

        super(context, 0, film);
        Films = film;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Film current = Films.get(position);


        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }


        TextView title = view.findViewById(R.id.titleview);
        title.setText(current.getTitle());


        TextView year = view.findViewById(R.id.yearview);
        year.setText(current.getYear());


        TextView rating = view.findViewById(R.id.ratingview);
        rating.setText(formatRating(current.getRating()));

        TextView runtime = view.findViewById(R.id.runtimeview);
        runtime.setText(current.getRunTime());

        return view;
    }

    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(rating);
    }
}
