package com.example.film;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

class FilmAdapter extends ArrayAdapter<Film> {

    private List<Film> mFilms;

    FilmAdapter(@NonNull Context context, @NonNull List<Film> films) {

        super(context, 0, films);
        mFilms = films;

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Film current = mFilms.get(position);


        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView title = view.findViewById(R.id.titleview);
        title.setText(current.getTitle());


        TextView language = view.findViewById(R.id.languageview);
        language.setText(current.getLanguage());


        TextView year = view.findViewById(R.id.yearview);
        year.setText(String.valueOf(current.getYear()));


        TextView rating = view.findViewById(R.id.ratingview);
        rating.setText(formatRating(current.getRating()) + "★");


        GradientDrawable ratingCircle = (GradientDrawable) rating.getBackground();
        ratingCircle.setColor(getRatingColor(current.getRating()));


        TextView runtime = view.findViewById(R.id.runtimeview);
        runtime.setText(current.getRunTime());

        return view;
    }

    private int getRatingColor(double rating) {

        int ratingColorResourceId;
        int ratingFloor = (int) Math.floor(rating);
        switch (ratingFloor) {
            case 0:
            case 1:
                ratingColorResourceId = R.color.rating1;
                break;
            case 2:
                ratingColorResourceId = R.color.rating2;
                break;
            case 3:
                ratingColorResourceId = R.color.rating3;
                break;
            case 4:
                ratingColorResourceId = R.color.rating4;
                break;
            case 5:
                ratingColorResourceId = R.color.rating5;
                break;
            case 6:
                ratingColorResourceId = R.color.rating6;
                break;
            case 7:
                ratingColorResourceId = R.color.rating7;
                break;
            case 8:
                ratingColorResourceId = R.color.rating8;
                break;
            case 9:
                ratingColorResourceId = R.color.rating9;
                break;
            default:
                ratingColorResourceId = R.color.rating10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), ratingColorResourceId);
    }

    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(rating);
    }
}
