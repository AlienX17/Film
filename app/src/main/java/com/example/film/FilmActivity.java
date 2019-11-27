package com.example.film;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
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

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minRating = sharedPrefs.getString(
                getString(R.string.settings_min_rating_key),
                getString(R.string.settings_min_rating_default));

        String itemNumber = sharedPrefs.getString(
                getString(R.string.settings_item_number_key),
                getString(R.string.settings_item_number_default));

        String sort_by = sharedPrefs.getString(
                getString(R.string.settings_sort_by_key),
                getString(R.string.settings_sort_by_default)
        );

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", itemNumber);
        uriBuilder.appendQueryParameter("minimum_rating", minRating);
        uriBuilder.appendQueryParameter("sort_by", sort_by);

        return new FilmLoader(this, uriBuilder.toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}