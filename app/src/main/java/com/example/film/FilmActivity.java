package com.example.film;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FilmActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Film>> {

    ArrayList<Film> films = new ArrayList<>();
    FilmAdapter mAdapter;
    ListView filmListView;

    private static final String REQUEST_URL = "https://api.myjson.com/bins/18wny6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filmListView = findViewById(R.id.list);

        mAdapter = new FilmAdapter(this, films);
        filmListView.setAdapter(mAdapter);

        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = films.get(i).getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<Film>> onCreateLoader(int i, Bundle bundle) {
        return new FilmLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Film>> loader, List<Film> earthquakes) {
        mAdapter.clear();
        if (earthquakes == null) {
            return;
        }
        mAdapter.addAll(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<Film>> loader) {
        mAdapter.clear();
    }
}