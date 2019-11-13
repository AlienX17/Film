package com.example.film;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class CustomAdapter extends BaseAdapter {

    private Context applicationContext;
    private int item;
    private List<JsonModel> jsonModels;

    CustomAdapter(Context applicationContext, int item, List<JsonModel> jsonModels) {

        this.applicationContext = applicationContext;
        this.item = item;
        this.jsonModels = jsonModels;

    }

    @Override
    public int getCount() {
        return jsonModels.size();
    }

    @Override
    public Object getItem(int i) {
        return jsonModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view =  layoutInflater.inflate(R.layout.item,viewGroup,false);

        }

        TextView title,year,rating,runTime;


        title = view.findViewById(R.id.title);
        year = view.findViewById(R.id.year);
        rating = view.findViewById(R.id.rating);
        runTime = view.findViewById(R.id.runtime);


        title.setText(jsonModels.get(i).getTitle());
        year.setText(jsonModels.get(i).getYear());
        rating.setText((float) jsonModels.get(i).getRating());
        runTime.setText(jsonModels.get(i).getRunTime());

        return view;
    }
}