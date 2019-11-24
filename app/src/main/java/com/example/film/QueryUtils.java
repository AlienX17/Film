package com.example.film;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class QueryUtils {

    private QueryUtils() {
    }

    static  List<Film> fetchFilmData(String requestUrl) {

        URL url = createUrl(requestUrl);
        List<Film> films = null;

        try {
            String jsonResponse = makeHttpRequest(url);
            films = extractFeaturesFromJson(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return films;
    }


    private static ArrayList<Film> extractFeaturesFromJson(String jsonResponse) {

        if(jsonResponse == null) {
            return null;
        }

        ArrayList<Film> films = new ArrayList<>();

        try {

            JSONObject object = new JSONObject(jsonResponse);
            JSONObject data = object.getJSONObject("data");
            JSONArray movies = data.getJSONArray("movies");

            for(int i = 0; i < movies.length(); i++) {

                JSONObject details = movies.getJSONObject(i);

                String title = details.getString("title");
                int year = details.getInt("year");
                double rating = details.getDouble("rating");
                int runTime = details.getInt("runtime");
                String filmUrl = details.getString("url");
                String language = details.getString("language");

                Film film = new Film(title, year, rating, runTime, filmUrl, language);

                films.add(film);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return films;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        if(stringUrl == null) {
            return null;
        }
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        if(url == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream (InputStream inputStream) {
        InputStreamReader streamReader;
        BufferedReader reader;
        StringBuilder result = new StringBuilder();

        if(inputStream == null) {
            return null;
        }

        streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        reader = new BufferedReader(streamReader);
        try {
            String line = reader.readLine();
            while (line != null) {

                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}