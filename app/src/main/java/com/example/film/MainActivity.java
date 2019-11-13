package com.example.film;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        JsonWork jsonWork = new JsonWork();
        jsonWork.execute();
    }


    private class JsonWork extends AsyncTask<String,String, List<JsonModel>>{

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String  FullJsonData;

        @Override
        protected List<JsonModel> doInBackground(String... strings) {

            try {
                URL url = new URL("https://api.myjson.com/bins/1afd0a");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer= new StringBuffer();
                String line="";

                while ((line= bufferedReader.readLine())!= null){

                    stringBuffer.append(line);

                }
                FullJsonData =  stringBuffer.toString();
                List<JsonModel> jsonModelList = new ArrayList<>();

                JSONObject jsonStartingObject = new JSONObject(FullJsonData);
                JSONArray  jsonStudentArray = jsonStartingObject.getJSONArray("movies");


                for(int i=0; i<jsonStudentArray.length(); i++) {

                    JSONObject jsonUnderArrayObject = jsonStudentArray.getJSONObject(i);

                    JsonModel jsonModel = new JsonModel();
                    jsonModel.setTitle(jsonUnderArrayObject.getString("title"));
                    jsonModel.setYear(jsonUnderArrayObject.getInt("year"));
                    jsonModel.setRating((float) jsonUnderArrayObject.getDouble("rating"));
                    jsonModel.setRunTime(jsonUnderArrayObject.getInt("runtime"));
                    jsonModelList.add(jsonModel);
                }

                return jsonModelList;


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally{

                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override        protected void onPostExecute(List<JsonModel> jsonModels) {
            super.onPostExecute(jsonModels);
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),R.layout.item,jsonModels);
            list.setAdapter(adapter);

        }
    }

}
