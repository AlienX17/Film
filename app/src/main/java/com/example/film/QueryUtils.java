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
            JSONArray features = object.getJSONArray("data");

            for(int i = 0; i < features.length(); i++) {
                JSONObject properties = features.getJSONObject(i).getJSONObject("movies");

                String title = properties.getString("title");
                int year = properties.getInt("year");
                double rating = properties.getDouble("rating");
                int runtime = properties.getInt("runtime");
                String filmUrl = properties.getString("url");

                Film film = new Film(title, year, rating, runtime, filmUrl);

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