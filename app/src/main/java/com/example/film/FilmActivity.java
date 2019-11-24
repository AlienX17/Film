package com.example.film;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FilmActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Film>> {

    private static final int FILM_LOADER_ID = 1;
    ArrayList<Film> films = new ArrayList<>();
    FilmAdapter mAdapter;
    ListView filmListView;
    TextView mEmptyState;
    ProgressBar mProgressBar;

    private static final String REQUEST_URL = "https://yts.lt/api/v2/list_movies.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyState = findViewById(R.id.empty_view);
        mProgressBar = findViewById(R.id.progressBar);

        // Checking the internet connection before initializing the loader
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager  = getLoaderManager();
            loaderManager.initLoader(FILM_LOADER_ID, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyState.setText(R.string.noConnection);
        }

        filmListView = findViewById(R.id.list);

        filmListView.setEmptyView(mEmptyState);

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
    public void onLoadFinished(Loader<List<Film>> loader, List<Film> films) {
        mAdapter.clear();
        if (films == null) {
            return;
        }
        mAdapter.addAll(films);

        mProgressBar.setVisibility(View.GONE);
        mEmptyState.setText(R.string.noFilms);

    }

    @Override
    public void onLoaderReset(Loader<List<Film>> loader) {
        mAdapter.clear();
    }

}